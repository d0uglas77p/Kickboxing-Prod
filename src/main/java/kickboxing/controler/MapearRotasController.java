package kickboxing.controler;

import jakarta.servlet.http.HttpSession;
import kickboxing.model.Admin;
import kickboxing.service.AdminService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.format.DateTimeFormatter;

@Controller
public class MapearRotasController {

    private AdminService adminService;

    @GetMapping("/index")
    public String indexPage() {
        return "index";
    }

    @GetMapping("/admin")
    public String adminPage() {
        return "admin";
    }

    @GetMapping("/recuperarSenha")
    public String mostrarFormularioRecuperacao(@RequestParam("token") String token, Model model) {
        if (adminService.isTokenValido(token)) {
            model.addAttribute("token", token);  // PASSA O TOKEN PARA O THYMELEAF
            return "recuperarSenha";

        } else {
            model.addAttribute("errorMessage", "Token de recuperação inválido ou expirado.");
            return "redirect:/index";
        }
    }

    @GetMapping("/administracao")
    public String logado(HttpSession session, RedirectAttributes redirectAttributes, Model model) {
        if (session.getAttribute("adminLogado") == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Você precisa ser um administrador para acessar essa página.");
            return "redirect:/index";
        }

        Admin admin = (Admin) session.getAttribute("adminLogado");

        if (admin != null && admin.getDataRegistro() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String dataFormatada = admin.getDataRegistro().format(formatter);
            model.addAttribute("dataRegistroFormatada", dataFormatada);
        }

        model.addAttribute("admin", admin);
        return "administracao";
    }
}