package com.supermarketSouza.SupermarketSouza.request;

import com.supermarketSouza.SupermarketSouza.model.UserRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginSaveDTO extends LoginDTO{

  private String cpf;

  private UserRole role;

}
