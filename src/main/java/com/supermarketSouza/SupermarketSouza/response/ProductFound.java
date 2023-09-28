package com.supermarketSouza.SupermarketSouza.response;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductFound {


  private String codeProduct;

  private String nameProduct;

  private BigDecimal priceProduct;

}
