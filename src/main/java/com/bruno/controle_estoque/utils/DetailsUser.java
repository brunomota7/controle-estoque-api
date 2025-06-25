package com.bruno.controle_estoque.utils;

import com.bruno.controle_estoque.enums.Roles;
import com.bruno.controle_estoque.model.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class DetailsUser implements UserDetails {

    private Long id;
    private String username;
    private String password;
    private Roles role;
    private Users user;

    public DetailsUser(Users user) {
        this.user = user;
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public void setPassword(String password) {this.password = password;}
    public void setUsername(String username) {this.username = username;}
}
