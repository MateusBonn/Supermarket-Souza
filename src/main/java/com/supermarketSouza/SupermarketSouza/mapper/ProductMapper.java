package com.supermarketSouza.SupermarketSouza.mapper;

import com.supermarketSouza.SupermarketSouza.model.ProductBoughtModel;
import com.supermarketSouza.SupermarketSouza.model.ProductSoldModel;
import com.supermarketSouza.SupermarketSouza.model.ProductStorageModel;
import com.supermarketSouza.SupermarketSouza.request.ProductSellDTO;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

  public ProductStorageModel toEntityProductStorage(ProductBoughtModel productBoughtModel){
    return ProductStorageModel
        .builder()
        .codeProduct(productBoughtModel.getCodeProduct())
        .nameProduct(productBoughtModel.getNameProduct())
        .productQuantity(productBoughtModel.getProductQuantityBought())
        .registrationDateLastUpdate(LocalDateTime.now())
        .build();
  }

  public ProductSoldModel toEntityProductSold(ProductStorageModel productStorageModel,
                                              ProductSellDTO request,
                                              ProductBoughtModel productBoughtModel){
    return ProductSoldModel
        .builder()
        .codeProduct(productStorageModel.getCodeProduct())
        .nameProduct(productStorageModel.getNameProduct())
        .priceProductSold(productBoughtModel.getPriceProductToSell())
        .productQuantitySold(request.getProductQuantitySold())
        .registrationDateSold(LocalDateTime.now())
        .build();
  }

}
