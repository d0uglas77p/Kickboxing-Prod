package kickboxing.service;

import jakarta.transaction.Transactional;
import kickboxing.exception.*;
import kickboxing.model.Admin;
import kickboxing.repository.AdminRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    public Admin criarContaAdmin(Admin admin) {

        if (adminRepository.existsByEmail(admin.getEmail())) {
            throw new EmailJaCriadoException("E-mail já cadastrado");
        }

        if (adminRepository.existsByTelefone(admin.getTelefone())) {
            throw new TelefoneJaCriadoException("Número de telefone já cadastrado");
        }

        String senhaCriptografada = BCrypt.hashpw(admin.getSenha(), BCrypt.gensalt());
        admin.setSenha(senhaCriptografada);

        return adminRepository.save(admin);
    }

    public boolean isEmailCadastrado(String email) {
        return adminRepository.existsByEmail(email);
    }

    @Transactional
    public String gerarTokenRecuperacao(String email) {
        String token = UUID.randomUUID().toString();

        LocalDateTime dataExpiracao = LocalDateTime.now().plusHours(1);

        adminRepository.salvarTokenRecuperacao(email, token, dataExpiracao);

        return token;
    }

    public void enviarEmailRecuperacao(String email, String mensagem) {
        if (!isEmailCadastrado(email)) {
            throw new EmailNaoCriadoException("E-mail não cadastrado");
        }

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("Recuperação de Senha");
        mailMessage.setText(mensagem);
        javaMailSender.send(mailMessage);
    }

    public boolean isTokenValido(String token) {
        LocalDateTime agora = LocalDateTime.now();
        Admin admin = adminRepository.findByTokenRecuperacao(token, agora);
        return admin != null;
    }

    @Transactional
    public void atualizarSenha(String token, String novaSenha) {
        if (novaSenha.length() < 8) {
            throw new IllegalArgumentException("A senha deve ter pelo menos 8 caracteres.");
        }

        Admin admin = adminRepository.findByTokenRecuperacao(token, LocalDateTime.now());

        if (admin != null) {
            admin.setSenha(BCrypt.hashpw(novaSenha, BCrypt.gensalt()));
            admin.setTokenRecuperacao(null);
            admin.setTokenExpiracao(null);

            adminRepository.save(admin);
        } else {
            throw new TokenExpiradoException("O token expirou ou é inválido.");
        }
    }

    public Admin autenticarAdmin(String email, String senha) {
        Admin admin = adminRepository.findByEmail(email);

        if (admin == null) {
            throw new AutenticacaoException("E-mail ou senha incorretos");
        }

        if (!BCrypt.checkpw(senha, admin.getSenha())) {
            throw new AutenticacaoException("E-mail ou senha incorretos");
        }

        return admin;
    }
}