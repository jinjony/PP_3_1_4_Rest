package ru.kata.spring.boot_security.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;


@Service

public class UserService implements UserDetailsService {


    private final UserRepository userRepository;




    private final BCryptPasswordEncoder bcryptPasswordEncoder;

    @PersistenceContext
    private EntityManager entityManager;


    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bcryptPasswordEncoder) {
        this.userRepository = userRepository;

        this.bcryptPasswordEncoder = bcryptPasswordEncoder;
    }

    @Transactional
    public List<User> listUsers() {
        return userRepository.findAll();
    }


//    public void add(User user) {
//        userRepository.save(user);
//    }
    @Transactional
    public Boolean saveUser(User user) {

        user.setPassword(bcryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return true;
    }

    @Transactional
    public void saveRole(Role role) {
        entityManager.persist(role);
    }

    @Transactional
    public User getUser(long id) {
        return userRepository.getById(id);
    }
    @Transactional
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    @Transactional
    public void delete(long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public void update(User user) {
        user.setPassword(bcryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;

//        List<GrantedAuthority> authorities = getUserAuthority(user.getRoles());
//
//        return new org.springframework.security.core.userdetails.User(user.getUsername(),
//                user.getPassword(), user.isEnabled(), user.isAccountNonExpired(), user.isCredentialsNonExpired(),
//                user.isAccountNonLocked(),  authorities);
    }
//    private List<GrantedAuthority> getUserAuthority(List<Role> userRoles) {
//        List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
//        for (Role role : userRoles) {
//            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
//        }
//        return grantedAuthorities;
//    }
//    public static UserDetails fromUser(User user, List<GrantedAuthority> authorities) {
//        return new org.springframework.security.core.userdetails.User(user.getUsername(),
//                user.getPassword(), true, true, true, true,  authorities);
//    }

}
