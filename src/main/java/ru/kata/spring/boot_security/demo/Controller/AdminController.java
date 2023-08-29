package ru.kata.spring.boot_security.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserServiceImpl userServiceImpl;

    @Autowired
    public AdminController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping("/")
    public String printUsers(ModelMap modelMap, @RequestParam(required = false) final Long id) {
        modelMap.addAttribute("users", userServiceImpl.getUserList());
        if (id == null) {
            modelMap.addAttribute("user", new User());
        } else {
            modelMap.addAttribute("user", userServiceImpl.findUserById(id));
        }
        return "admin";
    }

    @GetMapping("/deleteUser")
    public String deleteEmployee(@RequestParam Long userId) {
        userServiceImpl.deleteUser(userId);
        return "redirect:";
    }



    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute User user) {
        if (user.getId() == null) {
            userServiceImpl.addUser(user);
        } else {
            userServiceImpl.updateUser(user);
        }
        return "redirect:";
    }
}
