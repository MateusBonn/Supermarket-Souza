package com.supermarketSouza.SupermarketSouza.mapper;

import com.supermarketSouza.SupermarketSouza.model.LoginModel;
import com.supermarketSouza.SupermarketSouza.request.LoginDTO;
import org.springframework.stereotype.Component;

@Component
public class LoginMapper {

  public LoginDTO toDto(LoginModel loginModel) {
    return new LoginDTO(loginModel.getUsername(),
                        loginModel.getPassword());
  }

}
