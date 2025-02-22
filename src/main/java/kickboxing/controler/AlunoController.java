package kickboxing.controler;

import kickboxing.model.Aluno;
import kickboxing.service.AlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.time.LocalDate;

@Controller
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    @PostMapping("/criarAluno")
    public String criarAluno(@RequestParam("registroAluno") String registroAluno,
                                 @RequestParam("nomeAluno") String nomeAluno,
                                 @RequestParam("cidadeAluno") String cidadeAluno,
                                 @RequestParam("graduacaoAluno") String graduacaoAluno,
                                 @RequestParam("academiaAluno") String academiaAluno,
                                 @RequestParam("responsavelAluno") String responsavelAluno,
                                 @RequestParam("nascimentoAluno") String nascimentoAluno,
                                 @RequestParam("imagemAluno") MultipartFile imagemAluno,
                                 RedirectAttributes redirectAttributes) {

        System.out.println("Registro do Aluno: " + registroAluno);
        System.out.println("Nome do Aluno: " + nomeAluno);
        System.out.println("Cidade do Aluno: " + cidadeAluno);
        System.out.println("Graduação do Aluno: " + graduacaoAluno);
        System.out.println("Academia do Aluno: " + academiaAluno);
        System.out.println("Responsável do Aluno: " + responsavelAluno);
        System.out.println("Nascimento do Aluno: " + nascimentoAluno);
        System.out.println("Imagem: " + imagemAluno.getOriginalFilename());

        try {
            Aluno aluno = new Aluno();
            aluno.setRegistroAluno(registroAluno);
            aluno.setNomeAluno(nomeAluno);
            aluno.setCidadeAluno(cidadeAluno);
            aluno.setGraduacaoAluno(graduacaoAluno);
            aluno.setAcademiaAluno(academiaAluno);
            aluno.setResponsavelAluno(responsavelAluno);
            aluno.setNascimentoAluno(LocalDate.parse(nascimentoAluno));

            alunoService.salvarAluno(aluno, imagemAluno);
            redirectAttributes.addFlashAttribute("successMessage", "Aluno cadastrado com sucesso!");
            return "redirect:/administracao";

        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao salvar a imagem: " + e.getMessage());
            return "redirect:/administracao";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao cadastrar aluno: " + e.getMessage());
            return "redirect:/administracao";
        }
    }
}