package ru.kata.spring.boot_security.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin")
    public String printUsers(ModelMap modelMap, @RequestParam(required = false) final Long id) {
        modelMap.addAttribute("users", userService.getUserList());
        if (id == null) {
            modelMap.addAttribute("user", new User());
        } else {
            modelMap.addAttribute("user", userService.findUserById(id));
        }
        return "admin";
    }

    @GetMapping("/admin/deleteUser")
    public String deleteEmployee(@RequestParam Long userId) {
        userService.deleteUser(userId);
        return "redirect:/admin";
    }

    @GetMapping("/user")
    public String userPage() {
        return "user";
    }


    @PostMapping("/admin/saveUser")
    public String saveUser(@ModelAttribute User user) {
        if (user.getId() == null) {
            userService.addUser(user);
        } else {
            userService.updateUser(user);
        }
        return "redirect:/admin";
    }
}
