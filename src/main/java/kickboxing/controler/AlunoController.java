package kickboxing.controler;

import kickboxing.model.Aluno;
import kickboxing.model.Professor;
import kickboxing.service.AlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

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
            return "redirect:/alunosAdm?refresh=" + System.currentTimeMillis();

        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao salvar a imagem: " + e.getMessage());
            return "redirect:/alunosAdm";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao cadastrar aluno: " + e.getMessage());
            return "redirect:/alunosAdm";
        }
    }

    public String listarAlunos(Model model) {
        List<Aluno> alunos = alunoService.listarAlunos();

        List<String> cidades = alunos.stream()
                .map(Aluno::getCidadeAluno)
                .distinct()
                .collect(Collectors.toList());

        model.addAttribute("cidades", cidades);
        model.addAttribute("alunos", alunos);
        return "alunosAdm";
    }

    @GetMapping("/pesquisarAlunos")
    @ResponseBody
    public List<Aluno> pesquisarAlunos(@RequestParam("opcoes-cidades-alunos") String cidadeAluno) {
        if (cidadeAluno == null || cidadeAluno.isEmpty()) {
            return alunoService.listarAlunos();
        } else {
            return alunoService.pesquisarAlunosPorCidade(cidadeAluno);
        }
    }

    @PostMapping("/alunos/{id}")
    public String excluirAluno(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            alunoService.excluirAluno(id);
            redirectAttributes.addFlashAttribute("successMessage", "Aluno excluído com sucesso!");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao excluir Aluno: " + e.getMessage());
        }
        return "redirect:/alunosAdm";
    }

    @PostMapping("/editarAluno")
    public String editarAluno(@RequestParam("idAluno") Long idAluno,
                                  @RequestParam("registroAluno") String registroAluno,
                                  @RequestParam("nomeAluno") String nomeAluno,
                                  @RequestParam("cidadeAluno") String cidadeAluno,
                                  @RequestParam("graduacaoAluno") String graduacaoAluno,
                                  @RequestParam("academiaAluno") String academiaAluno,
                                  @RequestParam("responsavelAluno") String responsavelAluno,
//                                  @RequestParam("nascimentoAluno") String nascimentoAluno,
                                  @RequestParam(value = "imagemAluno", required = false) MultipartFile imagemAluno,
                                  RedirectAttributes redirectAttributes) {
        try {
            Aluno aluno = alunoService.buscarAlunoPorId(idAluno);

            aluno.setRegistroAluno(registroAluno);
            aluno.setNomeAluno(nomeAluno);
            aluno.setCidadeAluno(cidadeAluno);
            aluno.setGraduacaoAluno(graduacaoAluno);
            aluno.setAcademiaAluno(academiaAluno);
            aluno.setResponsavelAluno(responsavelAluno);

//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//            LocalDate nascimentoLocalDate = LocalDate.parse(nascimentoAluno, formatter);
//            aluno.setNascimentoAluno(nascimentoLocalDate);

            alunoService.salvarAluno(aluno, imagemAluno);

            redirectAttributes.addFlashAttribute("successMessage", "Aluno atualizado com sucesso!");
            return "redirect:/alunosAdm?refresh=" + System.currentTimeMillis();

        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao salvar a imagem: " + e.getMessage());
            return "redirect:/alunosAdm";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao atualizar professor: " + e.getMessage());
            return "redirect:/alunosAdm";
        }
    }
}