package kickboxing.controler;

import kickboxing.model.Patrocinador;
import kickboxing.service.PatrocinadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
public class PatrocinadorController {

    @Autowired
    private PatrocinadorService patrocinadorService;

    @PostMapping("/criarPatrocinador")
    public String criarPatrocinador(@RequestParam("linkPatrocinador") String linkPatrocinador,
                                @RequestParam("imagemPatrocinador") MultipartFile imagemPatrocinador,
                                RedirectAttributes redirectAttributes) {

        System.out.println("Link do Patrocinador: " + linkPatrocinador);
        System.out.println("Imagem: " + imagemPatrocinador.getOriginalFilename());

        try {
            Patrocinador patrocinador = new Patrocinador();
            patrocinador.setLinkPatrocinador(linkPatrocinador);

            patrocinadorService.salvarPatrocinador(patrocinador, imagemPatrocinador);
            redirectAttributes.addFlashAttribute("successMessage", "Patrocinador cadastrado com sucesso!");
            return "redirect:/administracao";

        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao salvar a imagem: " + e.getMessage());
            return "redirect:/administracao";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao cadastrar patrocinador: " + e.getMessage());
            return "redirect:/administracao";
        }
    }
}