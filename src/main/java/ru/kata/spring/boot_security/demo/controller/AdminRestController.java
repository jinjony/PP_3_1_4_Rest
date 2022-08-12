package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;

@RestController
public class AdminRestController {

    private final UserService userService;

    private final RoleService roleService;

    @Autowired
    public AdminRestController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/api/user")
    public ResponseEntity<User> userInfoPage(Principal principal) {
        return new ResponseEntity<>(userService.findByUsername(principal.getName()), HttpStatus.OK);
    }

    @GetMapping("/api/admin")
    public ResponseEntity<List<User>> getUsers() {
        return new ResponseEntity<>(userService.listUsers(), HttpStatus.OK);

    }


    @GetMapping("/api/admin/{id}")
    public ResponseEntity<User> getById(@PathVariable("id") long id) {
       return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK);

    }

    @PostMapping( "/api/admin")
    public ResponseEntity<HttpStatus> createUser(@RequestBody User user) {
       // user.setRoles(roleService.getByName(roles));
        userService.saveUser(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping ("/api/admin/{id}")
    public ResponseEntity<HttpStatus> update(@RequestBody User user){

        //user.setRoles(roleService.getByName(roles));
        userService.update(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping ("/api/admin/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") long id) {
        userService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
