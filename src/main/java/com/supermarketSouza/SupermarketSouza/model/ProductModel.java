package com.supermarketSouza.SupermarketSouza.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "TB_PRODUCT")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductModel {


  private String codeProduct;

  private String nameProduct;

  private long productQuantity;

  private BigDecimal initialPriceProduct;

  private BigDecimal finalPriceProduct;

}
