package com.supermarketSouza.SupermarketSouza.response;

import com.supermarketSouza.SupermarketSouza.model.UserRole;
import lombok.Builder;

@Builder
public class LoginResponse {

  public String firstName;

  public UserRole role;
}
