package com.supermarketSouza.SupermarketSouza.repositories;

import com.supermarketSouza.SupermarketSouza.model.ProductBoughtModel;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductBoughtCustomRepository {

  public ProductBoughtModel findLatestProductBought(String codeProduct);

}
