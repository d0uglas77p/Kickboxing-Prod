package kickboxing.service;

import kickboxing.model.Aluno;
import kickboxing.model.Professor;
import kickboxing.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class AlunoService {

    @Autowired
    private AlunoRepository alunoRepository;

    private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/src/main/resources/static/upload/alunos";

    public void salvarAluno(Aluno aluno, MultipartFile imagemAluno) throws IOException {
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        if (!imagemAluno.isEmpty()) {
            String nomeArquivo = System.currentTimeMillis() + "_" + imagemAluno.getOriginalFilename();
            File destino = new File(uploadDir, nomeArquivo);
            imagemAluno.transferTo(destino);

            aluno.setImagemAluno("/upload/alunos/" + nomeArquivo);
        }

        alunoRepository.save(aluno);
    }

    public List<Aluno> listarAlunos() {
        return alunoRepository.findAll();
    }

    public List<Aluno> pesquisarAlunosPorCidade(String cidade) {
        return alunoRepository.findByCidadeAluno(cidade);
    }

    public void excluirAluno(Long id) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        // Remover imagem
        File imagem = new File(System.getProperty("user.dir") + "/src/main/resources/static" + aluno.getImagemAluno());
        if (imagem.exists()) {
            imagem.delete();
        }

        alunoRepository.deleteById(id);
    }

    public Aluno buscarAlunoPorId(Long id) {
        return alunoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
    }

    //* AMBIENTE DE PRODUÇÃO ACESSE O ARQUIVO ---- "PRODUCAO.MD" ---- *//
    //* IMPORTANTE PARA ENTENDER COMO VAI FUNCIONAR!!!!!!! *//
}