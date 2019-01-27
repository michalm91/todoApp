package com.mmatuszewski.todo.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mmatuszewski.todo.domain.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

@Getter
@AllArgsConstructor
public class UserPrinciple implements UserDetails {


    private Long id;
    private String username;

    @JsonIgnore
    private String password;

    private String email;

    private Collection<? extends GrantedAuthority> authorities;


    public static UserPrinciple build(User user) {

        return new UserPrinciple(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                Collections.emptyList()
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserPrinciple user = (UserPrinciple) o;
        return Objects.equals(id, user.id);
    }
}
