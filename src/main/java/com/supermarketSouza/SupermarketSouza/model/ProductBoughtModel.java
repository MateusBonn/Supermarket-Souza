package com.supermarketSouza.SupermarketSouza.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "TB_PRODUCT_BOUGHT")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductBoughtModel {

  @Id
  private String codeProduct;

  private String nameProduct;

  private long productQuantityBought;

  private BigDecimal priceProductBought;

  private BigDecimal priceProductToSell;

  private LocalDateTime registrationDate;

}
