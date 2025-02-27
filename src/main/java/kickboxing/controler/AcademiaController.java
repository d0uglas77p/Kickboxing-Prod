package kickboxing.controler;

import kickboxing.model.Academia;
import kickboxing.service.AcademiaService;
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
import java.util.List;

@Controller
public class AcademiaController {

    @Autowired
    private AcademiaService academiaService;

    public AcademiaController(AcademiaService academiaService) {
        this.academiaService = academiaService;
    }

    @PostMapping("/criarAcademia")
    public String criarAcademia(@RequestParam("nomeAcademia") String nomeAcademia,
                                @RequestParam("responsavelAcademia") String responsavelAcademia,
                                @RequestParam("enderecoAcademia") String enderecoAcademia,
                                @RequestParam("cidadeAcademia") String cidadeAcademia,
                                @RequestParam("contatoAcademia") String contatoAcademia,
                                @RequestParam("imagemAcademia") MultipartFile imagemAcademia,
                                RedirectAttributes redirectAttributes) {
        try {
            Academia academia = new Academia();
            academia.setNomeAcademia(nomeAcademia);
            academia.setResponsavelAcademia(responsavelAcademia);
            academia.setEnderecoAcademia(enderecoAcademia);
            academia.setCidadeAcademia(cidadeAcademia);
            academia.setContatoAcademia(contatoAcademia);

            academiaService.salvarAcademia(academia, imagemAcademia);

            redirectAttributes.addFlashAttribute("successMessage", "Academia cadastrada com sucesso!");
            return "redirect:/academiasAdm?refresh=" + System.currentTimeMillis();

        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao salvar a imagem: " + e.getMessage());
            return "redirect:/academiasAdm";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao cadastrar academia: " + e.getMessage());
            return "redirect:/academiasAdm";
        }
    }

    public String listarAcademias(Model model) {
        List<Academia> academias = academiaService.listarAcademias();
        List<String> cidades = academiaService.listarCidades();
        model.addAttribute("academias", academias);
        model.addAttribute("cidades", cidades);
        return "academiasAdm";
    }

    @PostMapping("/academias/{id}")
    public String excluirAcademia(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            academiaService.excluirAcademia(id);
            redirectAttributes.addFlashAttribute("successMessage", "Academia excluída com sucesso!");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao excluir Academia: " + e.getMessage());
        }
        return "redirect:/academiasAdm";
    }

    @GetMapping("/pesquisarAcademias")
    public String pesquisarAcademias(@RequestParam("opcoes-cidades") String cidade, Model model) {
        List<Academia> academias;

        if (cidade == null || cidade.isEmpty()) {
            academias = academiaService.listarAcademias();
        } else {
            academias = academiaService.pesquisarAcademias(cidade);
        }

        List<String> cidades = academiaService.listarCidades();

        model.addAttribute("academias", academias);
        model.addAttribute("cidades", cidades);
        return "academiasAdm";
    }
}