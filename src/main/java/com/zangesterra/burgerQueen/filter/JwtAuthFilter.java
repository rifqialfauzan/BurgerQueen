package com.zangesterra.burgerQueen.filter;


import com.zangesterra.burgerQueen.service.UserDetailService;
import com.zangesterra.burgerQueen.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailService userDetailService;
    final static Logger getLog = LoggerFactory.getLogger(JwtAuthFilter.class);


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String bearerToken = request.getHeader("Authorization"); // get bearerToken from header
        getLog.info(bearerToken);
        try {
            String jwtToken = getJwtToken(bearerToken); // get jwt token from bearer token
            getLog.info(jwtToken);

            if (Objects.nonNull(jwtToken) && jwtUtil.verifyToken(jwtToken)){ // check if token is null and verify the token
                String email = jwtUtil.getUsernameFromToken(jwtToken);

                UserDetails userDetails = userDetailService.loadUserByUsername(email);

                // authenticate user
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

//                getLog.info(String.valueOf(authentication.isAuthenticated()));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }catch (Exception e){
            getLog.info("Cannot authenticate user", e.getCause());
        }
        filterChain.doFilter(request,response);
    }

    private String getJwtToken(String bearerToken) {
        if (Objects.isNull(bearerToken) || !bearerToken.startsWith("Bearer ")){
            return null;
        }
        return bearerToken.substring(7);
    }
}
