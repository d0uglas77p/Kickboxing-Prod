package kickboxing.controler;

import kickboxing.model.Evento;
import kickboxing.service.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

@Controller
public class EventoController {

    @Autowired
    private EventoService eventoService;

    @PostMapping("/criarEvento")
    public String criarEvento(@RequestParam("nomeEvento") String nomeEvento,
                              @RequestParam("descricaoEvento") String descricaoEvento,
                              @RequestParam("dataEvento") String dataEvento,
                              @RequestParam("horaEvento") String horaEvento,
                              @RequestParam("imagemEvento") MultipartFile imagemEvento,
                              RedirectAttributes redirectAttributes) {

        System.out.println("Nome do Evento: " + nomeEvento);
        System.out.println("Descrição: " + descricaoEvento);
        System.out.println("Data: " + dataEvento);
        System.out.println("Hora: " + horaEvento);
        System.out.println("Imagem: " + imagemEvento.getOriginalFilename());

        try {
            Evento evento = new Evento();
            evento.setNomeEvento(nomeEvento);
            evento.setDescricaoEvento(descricaoEvento);
            evento.setDataEvento(LocalDate.parse(dataEvento));
            evento.setHoraEvento(LocalTime.parse(horaEvento));

            eventoService.salvarEvento(evento, imagemEvento);
            redirectAttributes.addFlashAttribute("successMessage", "Evento cadastrado com sucesso!");
            return "redirect:/administracao";

        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao salvar a imagem: " + e.getMessage());
            return "redirect:/administracao";

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao cadastrar evento: " + e.getMessage());
            return "redirect:/administracao";
        }
    }

}