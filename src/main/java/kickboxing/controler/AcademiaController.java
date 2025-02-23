package kickboxing.controler;

import kickboxing.model.Academia;
import kickboxing.service.AcademiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
public class AcademiaController {

    @Autowired
    private AcademiaService academiaService ;

    @PostMapping("/criarAcademia")
    public String criarAcademia(@RequestParam("nomeAcademia") String nomeAcademia,
                              @RequestParam("responsavelAcademia") String responsavelAcademia,
                              @RequestParam("enderecoAcademia") String enderecoAcademia,
                              @RequestParam("cidadeAcademia") String cidadeAcademia,
                              @RequestParam("contatoAcademia") String contatoAcademia,
                              @RequestParam("imagemAcademia") MultipartFile imagemAcademia,
                              RedirectAttributes redirectAttributes) {

        System.out.println("Nome da Academia: " + nomeAcademia);
        System.out.println("Rsponsável Técnico: " + responsavelAcademia);
        System.out.println("Endereço da Academia: " + enderecoAcademia);
        System.out.println("Cidade da Academia: " + cidadeAcademia);
        System.out.println("Contato da Academia: " + contatoAcademia);
        System.out.println("Imagem: " + imagemAcademia.getOriginalFilename());

        try {
            Academia academia = new Academia();
            academia.setNomeAcademia(nomeAcademia);
            academia.setResponsavelAcademia(responsavelAcademia);
            academia.setEnderecoAcademia(enderecoAcademia);
            academia.setCidadeAcademia(cidadeAcademia);
            academia.setContatoAcademia(contatoAcademia);

            academiaService.salvarAcademia(academia, imagemAcademia);
            redirectAttributes.addFlashAttribute("successMessage", "Academia cadastrada com sucesso!");
            return "redirect:/academiasAdm";

        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao salvar a imagem: " + e.getMessage());
            return "redirect:/academiasAdm";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao cadastrar academia: " + e.getMessage());
            return "redirect:/academiasAdm";
        }
    }
}