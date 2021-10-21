package com.turkcell.pollservice.auth;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.turkcell.pollservice.util.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class UserPrincipal implements UserDetails {

    private Long id;
    private String name;
    private String username;
    @JsonIgnore
    private String email;
    @JsonIgnore
    private String password;

    String roles;

    public UserPrincipal() {

    }

    public UserPrincipal(Claims claims) {
        this.id =  Long.parseLong((String)claims.get("id"));
        this.username = (String)claims.get("username");
        this.name = (String)claims.get("name");
        this.roles = String.join(JwtUtil.CLAIM_DELIMETER,(String)claims.get("authorities"));
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        if(!StringUtils.isEmpty(roles)){
            List<GrantedAuthority> authorities = Arrays.stream(roles.split("#")).map(role ->
                    new SimpleGrantedAuthority(role)
            ).collect(Collectors.toList());

            return authorities;
        }

        return new ArrayList<GrantedAuthority>();
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