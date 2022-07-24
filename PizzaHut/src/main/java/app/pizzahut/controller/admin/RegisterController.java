package app.pizzahut.controller.admin;

import app.pizzahut.request.UserModelRequest;
import app.pizzahut.response.UserModelRes;
import app.pizzahut.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
public class RegisterController {

    @Autowired
    UserService userService;

    @GetMapping("/dangky")
    public String showDangKyForm(Model model, RedirectAttributes redirectAttributes) {
        model.addAttribute("userModelRes", new UserModelRes());
        return "dangky";
    }

    @PostMapping("/dangky")
    public String doDangKy(UserModelRes userModelRes, RedirectAttributes redirectAttributes) {
        log.info("-------------" + userModelRes.toString());
        try {
            userService.register(userModelRes);
            log.info("-------------doRegis");
            return "redirect:/login";
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
            return "redirect:/dangky";
        }
    }

}
