package ru.kata.spring.boot_security.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;

    @Autowired
    public AdminController(UserService userServiceImpl) {
        this.userService = userServiceImpl;
    }

    @ResponseBody
    @RequestMapping(value = "/loadUser/{id}", method = RequestMethod.GET)
    public User loadEntity(@PathVariable("id") Long id) {
        return userService.findUserById(id);
    }


    @GetMapping("/")
    public String printUsers(ModelMap modelMap, @RequestParam(required = false) final Long id) {
        modelMap.addAttribute("users", userService.getUserList());
        if (id == null) {
            modelMap.addAttribute("user", new User());
        } else {
            modelMap.addAttribute("user", userService.findUserById(id));
        }
        return "admin";
    }

    @GetMapping("/deleteUser")
    public String deleteEmployee(@RequestParam Long userId) {
        userService.deleteUser(userId);
        return "redirect:";
    }



    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute User user) {
        if (user.getId() == null) {
            userService.addUser(user);
        } else {
            userService.updateUser(user);
        }
        return "redirect:";
    }
}
