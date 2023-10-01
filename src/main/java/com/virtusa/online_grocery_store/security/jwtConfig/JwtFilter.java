package com.virtusa.online_grocery_store.security.jwtConfig;


import com.virtusa.online_grocery_store.security.jwtutils.JwtUtil;
import com.virtusa.online_grocery_store.security.userServices.UserDetailServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private static final Logger logMessage = LoggerFactory.getLogger(JwtFilter.class.getName());
    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    UserDetailServiceImpl userDetailServiceImpl;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String fulltoken = request.getHeader("Authorization");
        String username=null;
        String password=null;
        String token;
        if(fulltoken != null && fulltoken.startsWith("Bearer "))
        {
            token = fulltoken.substring(7);
            try {
                username = jwtUtil.extractUsername(token);
            }catch (Exception e) {
                e.printStackTrace();
            }
            UserDetails uds = userDetailServiceImpl.loadUserByUsername(username);

            if(username != null && SecurityContextHolder.getContext().getAuthentication() == null)
            {
                UsernamePasswordAuthenticationToken unamePwdAuthToken =
                        new UsernamePasswordAuthenticationToken(uds, null, uds.getAuthorities());

                unamePwdAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(unamePwdAuthToken);
            }else {
                logMessage.error("Invalid Token");
            }


        }
        filterChain.doFilter(request, response);
    }

}
