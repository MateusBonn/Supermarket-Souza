package com.supermarketSouza.SupermarketSouza.request;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ProductDTO {

  private String nameProduct;

  private String codeProduct;

  private long productQuantity;

  private BigDecimal initialPriceProduct;

  private BigDecimal finalPriceProduct;

}
