package com.supermarketSouza.SupermarketSouza.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductSellDTO {

  @NotBlank(message = "Code of product can't be blank")
  @NotNull(message = "Code of product can't be null")
  private String codeProduct;

  private Long productQuantitySold;

}
