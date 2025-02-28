package kickboxing.controler;

import kickboxing.model.Professor;
import kickboxing.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ProfessorController {

    @Autowired
    private ProfessorService professorService;

    @PostMapping("/criarProfessor")
    public String criarProfessor(@RequestParam("registroProfessor") String registroProfessor,
                                 @RequestParam("nomeProfessor") String nomeProfessor,
                                 @RequestParam("cidadeProfessor") String cidadeProfessor,
                                 @RequestParam("graduacaoProfessor") String graduacaoProfessor,
                                 @RequestParam("equipeProfessor") String equipeProfessor,
                                 @RequestParam("nascimentoProfessor") String nascimentoProfessor,
                                 @RequestParam("imagemProfessor") MultipartFile imagemProfessor,
                                 RedirectAttributes redirectAttributes) {
        try {
            Professor professor = new Professor();
            professor.setRegistroProfessor(registroProfessor);
            professor.setNomeProfessor(nomeProfessor);
            professor.setCidadeProfessor(cidadeProfessor);
            professor.setGraduacaoProfessor(graduacaoProfessor);
            professor.setEquipeProfessor(equipeProfessor);
            professor.setNascimentoProfessor(LocalDate.parse(nascimentoProfessor));

            professorService.salvarProfessor(professor, imagemProfessor);

            redirectAttributes.addFlashAttribute("successMessage", "Professor cadastrado com sucesso!");
            return "redirect:/professoresAdm?refresh=" + System.currentTimeMillis();

        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao salvar a imagem: " + e.getMessage());
            return "redirect:/professoresAdm";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao cadastrar professor: " + e.getMessage());
            return "redirect:/professoresAdm";
        }
    }

    public String listarProfessores(Model model) {
        List<Professor> professores = professorService.listarProfessores();

        // Extrair as cidades dos professores para preencher o select
        List<String> cidades = professores.stream()
                .map(Professor::getCidadeProfessor)
                .distinct()
                .collect(Collectors.toList());

        model.addAttribute("cidades", cidades);  // Passando a lista de cidades para o modelo
        model.addAttribute("professores", professores);
        return "professoresAdm";
    }

    @GetMapping("/pesquisarProfessores")
    public String pesquisarProfessores(@RequestParam(required = false) String cidadeProfessor, Model model) {
        List<Professor> professores;
        if (cidadeProfessor != null && !cidadeProfessor.isEmpty()) {
            professores = professorService.pesquisarProfessoresPorCidade(cidadeProfessor);
        } else {
            professores = professorService.listarProfessores();
        }

        model.addAttribute("professores", professores);
        return "professoresAdm";
    }

    @PostMapping("/professores/{id}")
    public String excluirProfessor(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            professorService.excluirProfessor(id);
            redirectAttributes.addFlashAttribute("successMessage", "Professor excluído com sucesso!");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao excluir Professor: " + e.getMessage());
        }
        return "redirect:/professoresAdm";
    }

    @PostMapping("/editarProfessor")
    public String editarProfessor(@RequestParam("idProfessor") Long idProfessor,
                                  @RequestParam("registroProfessor") String registroProfessor,
                                  @RequestParam("nomeProfessor") String nomeProfessor,
                                  @RequestParam("cidadeProfessor") String cidadeProfessor,
                                  @RequestParam("graduacaoProfessor") String graduacaoProfessor,
                                  @RequestParam("equipeProfessor") String equipeProfessor,
//                                  @RequestParam("nascimentoProfessor") String nascimentoProfessor,
                                  @RequestParam(value = "imagemProfessor", required = false) MultipartFile imagemProfessor,
                                  RedirectAttributes redirectAttributes) {
        try {
            Professor professor = professorService.buscarProfessorPorId(idProfessor);

            professor.setRegistroProfessor(registroProfessor);
            professor.setNomeProfessor(nomeProfessor);
            professor.setCidadeProfessor(cidadeProfessor);
            professor.setGraduacaoProfessor(graduacaoProfessor);
            professor.setEquipeProfessor(equipeProfessor);

//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//            LocalDate nascimentoLocalDate = LocalDate.parse(nascimentoProfessor, formatter);
//            professor.setNascimentoProfessor(nascimentoLocalDate);

            professorService.salvarProfessor(professor, imagemProfessor);

            redirectAttributes.addFlashAttribute("successMessage", "Professor atualizado com sucesso!");
            return "redirect:/professoresAdm?refresh=" + System.currentTimeMillis();

        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao salvar a imagem: " + e.getMessage());
            return "redirect:/professoresAdm";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao atualizar professor: " + e.getMessage());
            return "redirect:/professoresAdm";
        }
    }
}