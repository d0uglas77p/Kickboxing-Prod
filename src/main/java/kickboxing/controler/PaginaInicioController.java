package kickboxing.controler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PaginaInicioController {

    @GetMapping("/index")
    public String indexPage() {
        return "index";
    }

    @GetMapping("/admin")
    public String adminPage() {
        return "admin";
    }

    @PostMapping("/index")
    public String indexRedirecionar() {
        return "redirect:/index";
    }

    @PostMapping("/admin")
    public String adminRedirecionar() {
        return "redirect:/admin";
    }
}
