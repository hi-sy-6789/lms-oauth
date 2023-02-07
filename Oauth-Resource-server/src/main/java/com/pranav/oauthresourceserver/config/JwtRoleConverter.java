package com.pranav.oauthresourceserver.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.ArrayList;
import java.util.Collection;

import java.util.stream.Collectors;

@Slf4j
public class JwtRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    @Override
    public Collection convert(Jwt jwt) {
        @SuppressWarnings("unchecked")
        Collection<String> roles = (Collection<String>) jwt.getClaims().get("roles");

        if (roles == null || roles.isEmpty()) {
            return new ArrayList();
        }
        return roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

}