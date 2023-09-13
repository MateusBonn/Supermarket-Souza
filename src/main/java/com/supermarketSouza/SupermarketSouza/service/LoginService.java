package com.supermarketSouza.SupermarketSouza.service;

import com.supermarketSouza.SupermarketSouza.exception.RefreshTokenException;
import com.supermarketSouza.SupermarketSouza.mapper.LoginMapper;
import com.supermarketSouza.SupermarketSouza.model.LoginModel;
import com.supermarketSouza.SupermarketSouza.repositories.LoginRepository;
import com.supermarketSouza.SupermarketSouza.repositories.RefreshTokenRepository;
import com.supermarketSouza.SupermarketSouza.request.LoginDTO;
import com.supermarketSouza.SupermarketSouza.response.SecurityResponse;
import com.supermarketSouza.SupermarketSouza.utils.Builder;
import java.time.Instant;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LoginService {

  final LoginRepository loginRepository;
  final LoginMapper loginMapper;
  final RefreshTokenRepository refreshTokenRepository;
  final Builder utils;
  final AuthenticationManager authenticationManager;
  final TokenService tokenService;

  public SecurityResponse login(LoginDTO loginDTO){
      var usernamePassword =
          new UsernamePasswordAuthenticationToken(loginDTO.getUsername(),
                                                  loginDTO.getPassword());
      var auth = this.authenticationManager.authenticate(usernamePassword);

      var token = tokenService.generateToken((LoginModel) auth.getPrincipal());
      var refreshToken = tokenService.createRefreshToken((LoginModel) auth.getPrincipal());

      return SecurityResponse.builder()
          .login(utils.loginResponse(loginDTO))
          .token(utils.autenticatedResponse(token, refreshToken))
          .build();
  }

  public SecurityResponse refreshToken(String token) throws BadCredentialsException{
    var refreshToken = refreshTokenRepository.findByTokenAndExpiryDate(token, Instant.now());
    if(refreshToken.isPresent()) {
        var newToken = UUID.randomUUID().toString();
        tokenService.changeToken(refreshToken.get(), newToken);
        refreshToken = refreshTokenRepository.findByTokenAndExpiryDate(newToken,
                                                                       Instant.now());
        var loginModel = loginRepository.findUser(refreshToken.get().getLogin().getUsername());
        return SecurityResponse.builder()
            .login(utils.loginResponse(loginMapper.toDto(loginModel)))
            .token(utils.autenticatedResponse(tokenService.generateToken(loginModel),
                                              refreshToken.get()))
            .build();
    }
    throw new RefreshTokenException("Error");
  }

}
