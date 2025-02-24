package kickboxing.controler;

import jakarta.servlet.http.HttpSession;
import kickboxing.model.Admin;
import kickboxing.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.format.DateTimeFormatter;

@Controller
public class MapearRotasController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/index")
    public String indexPage() {
        return "index";
    }

    @GetMapping("/admin")
    public String adminPage() {
        return "admin";
    }

    private boolean isAdminLogado(HttpSession session) {
        return session.getAttribute("adminLogado") != null;
    }

    private String verificarSessao(HttpSession session, RedirectAttributes redirectAttributes) {
        if (!isAdminLogado(session)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Você precisa ser um administrador autenticado.");
            return "redirect:/index";
        }
        return null;
    }

    @GetMapping("/academiasAdm")
    public String academiasAdmPage(HttpSession session, RedirectAttributes redirectAttributes) {
        String redirecionamento = verificarSessao(session, redirectAttributes);
        return redirecionamento != null ? redirecionamento : "academiasAdm";
    }

    @GetMapping("/alunosAdm")
    public String alunosAdmPage(HttpSession session, RedirectAttributes redirectAttributes) {
        String redirecionamento = verificarSessao(session, redirectAttributes);
        return redirecionamento != null ? redirecionamento : "alunosAdm";
    }

    @GetMapping("/eventosAdm")
    public String eventosAdm(HttpSession session, RedirectAttributes redirectAttributes) {
        String redirecionamento = verificarSessao(session, redirectAttributes);
        return redirecionamento != null ? redirecionamento : "eventosAdm";
    }

    @GetMapping("/patrocinadoresAdm")
    public String patrocinadoresAdmPage(HttpSession session, RedirectAttributes redirectAttributes) {
        String redirecionamento = verificarSessao(session, redirectAttributes);
        return redirecionamento != null ? redirecionamento : "patrocinadoresAdm";
    }

    @GetMapping("/professoresAdm")
    public String professoresAdmPage(HttpSession session, RedirectAttributes redirectAttributes) {
        String redirecionamento = verificarSessao(session, redirectAttributes);
        return redirecionamento != null ? redirecionamento : "professoresAdm";
    }

    @GetMapping("/rankingAdm")
    public String rankingAdmPage(HttpSession session, RedirectAttributes redirectAttributes) {
        String redirecionamento = verificarSessao(session, redirectAttributes);
        return redirecionamento != null ? redirecionamento : "rankingAdm";
    }

    @GetMapping("/recuperarSenha")
    public String mostrarFormularioRecuperacao(@RequestParam("token") String token, Model model) {
        if (adminService.isTokenValido(token)) {
            model.addAttribute("token", token);
            return "recuperarSenha";

        } else {
            model.addAttribute("errorMessage", "Token de recuperação inválido ou expirado.");
            return "redirect:/index";
        }
    }

    @GetMapping("/administracao")
    public String logado(HttpSession session, RedirectAttributes redirectAttributes, Model model) {
        String redirecionamento = verificarSessao(session, redirectAttributes);
        if (redirecionamento != null) {
            return redirecionamento;
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