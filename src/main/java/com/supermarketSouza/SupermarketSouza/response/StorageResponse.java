package com.supermarketSouza.SupermarketSouza.response;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class StorageResponse {

  private String codeProduct;

  private String nameProduct;

  private BigDecimal priceProduct;

}
