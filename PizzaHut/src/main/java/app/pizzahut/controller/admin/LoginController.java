package app.pizzahut.controller.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
public class LoginController {

    @GetMapping("/login")
    public String viewLoginForm() {
        log.info("-----------Login");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "login";
        }

        log.info("-------u:" + authentication.getName());
        return "redirect:/admin/thanhvien";
    }

    @PostMapping("/login")
    public String doLogin() {
        log.info("-----------Do Login");
        return "redirect:/admin/thanhvien";
    }

}
