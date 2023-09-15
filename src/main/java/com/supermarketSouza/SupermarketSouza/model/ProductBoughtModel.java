package com.supermarketSouza.SupermarketSouza.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "TB_PRODUCT_BOUGHT")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductBoughtModel implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID productBoughtId;

  private String codeProduct;

  private String nameProduct;

  private long productQuantityBought;

  private BigDecimal priceProductBought;

  private BigDecimal priceProductToSell;

  private LocalDateTime registrationDate;
}
