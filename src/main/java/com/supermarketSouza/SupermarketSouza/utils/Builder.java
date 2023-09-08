package com.supermarketSouza.SupermarketSouza.utils;

import com.supermarketSouza.SupermarketSouza.model.LoginModel;
import com.supermarketSouza.SupermarketSouza.repositories.LoginRepository;
import com.supermarketSouza.SupermarketSouza.request.LoginDTO;
import com.supermarketSouza.SupermarketSouza.response.AutenticatedResponse;
import com.supermarketSouza.SupermarketSouza.response.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class Builder {

  final LoginRepository loginRepository;

  public LoginResponse loginResponse(LoginDTO loginDTO){
    var user = user(loginDTO);
   return LoginResponse.builder()
            .firstName(extractFirstName(user.getFullName()))
            .role(user.getRole())
            .build();
  }

  public AutenticatedResponse autenticatedResponse(String accessToken, String refreshToken){
    return AutenticatedResponse.builder()
        .accessToken(accessToken)
        .refreshToken(refreshToken)
        .build();
  }

  private String extractFirstName(String fullName) {
    return fullName.substring(0, fullName.indexOf(" "));
  }

  private LoginModel user(LoginDTO loginDTO){
    return loginRepository.findUser(loginDTO.getUsername());
  }


}
