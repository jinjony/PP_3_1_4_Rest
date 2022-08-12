package ru.kata.spring.boot_security.demo.model;


import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;


    @Column(name = "name")
    private String name;
    @Transient
    @ManyToMany (mappedBy = "roles")
    private List<User> users = new ArrayList<>();

    public Role() {

    }
    public Role(Long id) {
        this.id = id;
    }

    public Role(String name){
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return getName();
    }



    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return name;

    }
}
