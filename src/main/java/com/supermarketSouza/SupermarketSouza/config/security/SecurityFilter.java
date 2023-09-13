package com.supermarketSouza.SupermarketSouza.config.security;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.supermarketSouza.SupermarketSouza.repositories.LoginRepository;
import com.supermarketSouza.SupermarketSouza.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
@Slf4j
public class SecurityFilter extends OncePerRequestFilter {

  final TokenService tokenService;
  final LoginRepository loginRepository;
  final UserDetailsServiceImpl userDetailsService;
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    try {
      log.info("doFilterInternal");
      var token = this.recoverToken(request);
      if (token != null) {
        var login = tokenService.validateToken(token);
        UserDetails user = userDetailsService.loadUserByUsername(login);

        var authentication = new UsernamePasswordAuthenticationToken(user,
                                                                     null,
                                                                     user.getAuthorities());
        //log.info(String.valueOf(SecurityContextHolder.getContext().setAuthentication(authentication)));
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
      filterChain.doFilter(request, response);
    }catch (TokenExpiredException e) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.setContentType(MediaType.APPLICATION_JSON_VALUE);
      response.getOutputStream().println("Token expired");
    }
  }

  private String recoverToken(HttpServletRequest request) {
    log.info("recoverToken");
    var authHeader = request.getHeader("Authorization");
    if(authHeader == null) return null;
    return authHeader.replace("Bearer ", "");
  }
}
