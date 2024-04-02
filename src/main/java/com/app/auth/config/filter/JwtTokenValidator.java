package com.app.auth.config.filter;

import java.io.IOException;
import java.util.Collection;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.app.auth.util.JwtUtils;
import com.auth0.jwt.interfaces.DecodedJWT;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtTokenValidator extends OncePerRequestFilter {


    private JwtUtils jwtUtils;

    public JwtTokenValidator(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @SuppressWarnings("null")
    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {
        
            String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);

            if(jwtToken != null){

                jwtToken = jwtToken.substring(7);

                DecodedJWT decodedJWT = jwtUtils.validateToken(jwtToken);

                String username = jwtUtils.extractUsername(decodedJWT);
                String authorities = jwtUtils.getSpecificClaim(decodedJWT, "authorities").asString();

                Collection<? extends GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);

                SecurityContext context = SecurityContextHolder.getContext();
                Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, grantedAuthorities);
                context.setAuthentication(authentication);
                SecurityContextHolder.setContext(context);
            }
            
            filterChain.doFilter(request, response);
    }

}
