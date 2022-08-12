package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @GetMapping()
    public String getUsers(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userService.findByUsername(userDetails.getUsername());
        model.addAttribute("users", userService.listUsers());
        model.addAttribute("user", user);
        model.addAttribute("newUser", new User());
        model.addAttribute("roles", roleService.getAllRoles());
        return "/admin";
    }

//    @GetMapping("/{id}")
//    public String getById(@PathVariable("id") long id, Model model) {
//        model.addAttribute("user", userService.getUser(id));
//        return "user";
//    }
//    @GetMapping("/new")
//    public String newUser(Model model) {
//        model.addAttribute("user", new User());
//        return "new";
//    }



    @PostMapping( "/new")
    public String userCreate(@ModelAttribute("user") User user, @RequestParam(value = "nameRoles", required = false) String roles) {
        user.setRoles(roleService.getByName(roles));
        userService.saveUser(user);
        return "redirect:/admin";
    }


//    @GetMapping("/edit/{id}")
//    public String edit(Model model, @PathVariable("id") long id) {
//        model.addAttribute("user", userService.getUser(id));
//        return "edit";
//    }

    @PatchMapping ("/edit/{id}")
    public String update(@ModelAttribute("user") User user, @PathVariable("id") long id,
                         @RequestParam(value = "nameRoles", required = false) String roles) {
        //user.setRoles(roleService.getByName(roles));
        userService.update(user);
        return "redirect:/admin";
    }

    @DeleteMapping ("/delete/{id}")
    public String delete(@PathVariable("id") long id) {
        userService.delete(id);
        return "redirect:/admin";
    }

}
