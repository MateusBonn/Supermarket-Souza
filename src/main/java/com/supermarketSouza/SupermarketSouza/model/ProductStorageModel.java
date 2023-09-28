package com.supermarketSouza.SupermarketSouza.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "TB_PRODUCT_STORAGE")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductStorageModel implements Serializable {

  @Id
  @Column(name = "code_product", unique = true)
  private String codeProduct;

  @Column(name = "name_product")
  private String nameProduct;

  @Column(name = "product_quantity")
  private long productQuantity;

  @Column(name = "registration_date_last_update")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime registrationDateLastUpdate;
}
