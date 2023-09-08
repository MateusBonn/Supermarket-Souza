package com.supermarketSouza.SupermarketSouza.service;

import com.supermarketSouza.SupermarketSouza.config.security.TokenService;
import com.supermarketSouza.SupermarketSouza.model.LoginModel;
import com.supermarketSouza.SupermarketSouza.repositories.LoginRepository;
import com.supermarketSouza.SupermarketSouza.request.LoginDTO;
import com.supermarketSouza.SupermarketSouza.response.SecurityResponse;
import com.supermarketSouza.SupermarketSouza.utils.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LoginService {

  final LoginRepository loginRepository;
  final Builder utils;
  final AuthenticationManager authenticationManager;
  final TokenService tokenService;

  public SecurityResponse login(LoginDTO loginDTO){
    var usernamePassword =
        new UsernamePasswordAuthenticationToken(loginDTO.getUsername(),
                                                loginDTO.getPassword());
    var auth = this.authenticationManager.authenticate(usernamePassword);

    var token = tokenService.generateToken((LoginModel) auth.getPrincipal());

    return SecurityResponse.builder()
        .login(utils.loginResponse(loginDTO))
        .token(utils.autenticatedResponse(token, "Ajustar"))
        .build();
  }

}
