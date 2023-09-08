package com.supermarketSouza.SupermarketSouza.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ProductBoughtDTO {

  @NotNull(message = "Name of product can't be null")
  private String nameProduct;

  @NotNull(message = "Code of product can't be null")
  @NotBlank(message = "Code of product can't be blank")
  private String codeProduct;

  @NotNull(message = "Quantity Bought of product can't be null")
  private Long productQuantityBought;

  @NotNull(message = "Price Bought of product can't be null")
  private BigDecimal priceProductBought;

  private BigDecimal priceProductToSell;

}
