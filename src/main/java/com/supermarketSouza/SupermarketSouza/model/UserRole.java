package com.supermarketSouza.SupermarketSouza.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserRole {

  ADMIN("admin"),
  USER("user");

  private String role;
}
