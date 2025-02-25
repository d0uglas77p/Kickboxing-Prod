package kickboxing.controler;

import kickboxing.model.Professor;
import kickboxing.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

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
        model.addAttribute("professores", professores);
        return "professoresAdm";
    }
}