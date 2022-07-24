package app.pizzahut.controller.admin;

import app.pizzahut.exception.UserNotFoundException;
import app.pizzahut.repo.RankRepo;
import app.pizzahut.repo.UserRepo;
import app.pizzahut.response.UserModelRes;
import app.pizzahut.repo.RoleRepo;
import app.pizzahut.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@Slf4j
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserRepo userRepo;

    @Autowired
    RoleRepo roleRepo;

    @Autowired
    RankRepo rankRepo;

    @GetMapping("/admin/thanhvien")
    public String showQuanLyThanhVienPage(Model model, Principal principal, Authentication authentication) {
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "login";
        }
        model.addAttribute("listUserRes", userService.findAllRes());
        return "admin_quanlythanhvien";
    }

    @GetMapping("/admin/thanhvien/view/{id}")
    public String viewUser(@PathVariable(name = "id") Long id, RedirectAttributes redirectAttributes, Model model) {
        try {
            UserModelRes userRes = userService.getRes(id);
            model.addAttribute("pageTitle", "Thông tin chi tiết thành viên");
            model.addAttribute("listRole", roleRepo.findAll());
            model.addAttribute("listRank", rankRepo.findAll());
            model.addAttribute("userRes", userRes);
        } catch (UserNotFoundException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/admin/thanhvien";
        }
        return "admin_viewthanhvien";
    }

    @GetMapping("/admin/thanhvien/create")
    public String createNewUser(Model model) {
        model.addAttribute("pageTitle", "Thêm mới thành viên");
        model.addAttribute("listRole", roleRepo.findAll());
        model.addAttribute("listRank", rankRepo.findAll());
        model.addAttribute("userRes", new UserModelRes());
        return "admin_viewthanhvien";
    }

    @GetMapping("/admin/thanhvien/delete/{id}")
    public String deleteUser(@PathVariable(name = "id") Long id, RedirectAttributes redirectAttributes, Model model) {
        try {
            userService.delete(id);
            redirectAttributes.addFlashAttribute("message", "Xóa thành công user với id: " + id +"!");
        } catch (UserNotFoundException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/admin/thanhvien";
    }

    @PostMapping ("/admin/thanhvien/save")
    public String saveUser(UserModelRes userRes,
                           RedirectAttributes redirectAttributes) {
        log.info("----------------------------------- doSave");
        log.info("----------------------------------- userRes:" + userRes.toString());

        log.info("-----------------------------------" + userRes.toString());

        try {
            UserModelRes res = userService.save(userRes);
            if (userRes.getId() == null) {
                redirectAttributes.addFlashAttribute("message", "Thêm mới thành công thành viên id: " + res.getId());
            } else {
                redirectAttributes.addFlashAttribute("message", "Cập nhập thành công thành viên id: " + res.getId());
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/admin/thanhvien";
    }

}
