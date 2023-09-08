package com.supermarketSouza.SupermarketSouza.response;

import lombok.Builder;

@Builder
public class AutenticatedResponse {

  public String accessToken;
  public String refreshToken;

}
