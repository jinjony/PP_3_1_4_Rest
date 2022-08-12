package ru.kata.spring.boot_security.demo.model;



import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Data
@Entity
@Table(name = "users")
public class User implements UserDetails {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;


   @Column(name = "username")
   private String username;

   @Column(name = "last_name")
   private String lastName;

   @Column(name = "email")
   private String email;

   @Column(name = "password")
   private String password;


   @ManyToMany (fetch = FetchType.EAGER)
   @JoinTable(name = "user_roles", joinColumns =  @JoinColumn(name = "user_id"),
           inverseJoinColumns =  @JoinColumn(name = "role_id"))

   private List<Role> roles = new ArrayList<Role>();

   public User(String username, String password, List<Role> roles) {
      this.username= username;
      this.password = password;
      this.roles = roles;
   }

   public User() {

   }

   @Override
   public Collection<? extends GrantedAuthority> getAuthorities() {

      return roles.stream()
              .map(role -> new SimpleGrantedAuthority(role.getAuthority()))
              .collect(Collectors.toList());
   }

   public String getRolesAsString() {
      StringBuilder sb = new StringBuilder();
      for (Role role : roles) {
         sb.append(role.toString().substring(5));
         sb.append(" ");
      }
      return sb.toString();
   }

   public void addRole(Role role) {
      roles.add(role);
   }

   @Override
   public String getPassword() {
      return password;
   }

   @Override
   public String getUsername() {
      return username;
   }

   @Override
   public boolean isAccountNonExpired() {
      return true;
   }

   @Override
   public boolean isAccountNonLocked() {
      return true;
   }

   @Override
   public boolean isCredentialsNonExpired() {
      return true;
   }

   @Override
   public boolean isEnabled() {
      return true;
   }


}
