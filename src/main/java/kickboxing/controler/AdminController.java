package kickboxing.controler;

import kickboxing.exception.EmailJaCriadoException;
import kickboxing.exception.TelefoneJaCriadoException;
import kickboxing.model.Admin;
import kickboxing.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/criarConta")
    public String criarConta(@ModelAttribute Admin admin, RedirectAttributes redirectAttributes) {

        try {
            Admin novoAdmin = adminService.criarContaAdmin(admin);
            redirectAttributes.addFlashAttribute("successMessage", "Cadastro realizado com sucesso!");
            return "redirect:/admin";

        } catch (EmailJaCriadoException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/admin";

        } catch (TelefoneJaCriadoException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/admin";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao cadastrar adminstrador: " + e.getMessage());
            return "redirect:/admin";
        }
    }

    @PostMapping("/recuperarConta")
    public String recuperarConta(@RequestParam("email") String email, RedirectAttributes redirectAttributes) {

        try {
            if (!adminService.isEmailCadastrado(email)) {
                redirectAttributes.addFlashAttribute("errorMessage", "Esse e-mail não está cadastrado!");
                return "redirect:/admin";
            }

            String token = adminService.gerarTokenRecuperacao(email);

            String urlRecuperacao = "http://localhost:8080/recuperarSenha?token=" + token;

            adminService.enviarEmailRecuperacao(email, "Clique no link para recuperar sua senha: " + urlRecuperacao);

            redirectAttributes.addFlashAttribute("successMessage", "Um e-mail foi enviado com as instruções.");
            return "redirect:/admin";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao processar a solicitação: " + e.getMessage());
            return "redirect:/admin";
        }
    }

    @PostMapping("/atualizarSenha")
    public String atualizarSenha(@RequestParam("token") String token, @RequestParam("senha") String novaSenha, RedirectAttributes redirectAttributes) {

        try {
            if (adminService.isTokenValido(token)) {
                adminService.atualizarSenha(token, novaSenha);
                redirectAttributes.addFlashAttribute("successMessage", "Senha atualizada com sucesso.");
                return "redirect:/admin";

            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Token inválido ou expirado.");
                return "redirect:/admin";
            }

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao atualizar a senha: " + e.getMessage());
            return "redirect:/admin";
        }
    }

    @GetMapping("/recuperarSenha")
    public String mostrarFormularioRecuperacao(@RequestParam("token") String token, Model model) {
        if (adminService.isTokenValido(token)) {
            model.addAttribute("token", token);  // PASSA O TOKEN PARA O THYMELEAF
            return "recuperarSenha";

        } else {
            model.addAttribute("errorMessage", "Token de recuperação inválido ou expirado.");
            return "redirect:/index";
        }
    }

}