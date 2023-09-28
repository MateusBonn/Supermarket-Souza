package com.supermarketSouza.SupermarketSouza.mapper;

import com.supermarketSouza.SupermarketSouza.model.ProductBoughtModel;
import com.supermarketSouza.SupermarketSouza.model.ProductSoldModel;
import com.supermarketSouza.SupermarketSouza.model.ProductStorageModel;
import com.supermarketSouza.SupermarketSouza.request.ProductSellDTO;
import com.supermarketSouza.SupermarketSouza.response.ProductFound;
import com.supermarketSouza.SupermarketSouza.response.StorageResponse;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
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

  public List<ProductStorageModel> toEntityProductStorage(List<ProductBoughtModel> productBoughtModel){
    return productBoughtModel.stream()
        .map(productBought -> ProductStorageModel
            .builder()
            .codeProduct(productBought.getCodeProduct())
            .nameProduct(productBought.getNameProduct())
            .productQuantity(productBought.getProductQuantityBought())
            .registrationDateLastUpdate(LocalDateTime.now())
            .build()).collect(Collectors.toList());
  }

  public ProductSoldModel toEntityProductSold(ProductStorageModel productStorageModel,
                                              ProductSellDTO request,
                                              BigDecimal priceProductSold){
    return ProductSoldModel
        .builder()
        .codeProduct(productStorageModel.getCodeProduct())
        .nameProduct(productStorageModel.getNameProduct())
        .priceProductSold(priceProductSold)
        .productQuantitySold(1)
        .registrationDateSold(LocalDateTime.now())
        .build();
  }

  public List<StorageResponse> toResponse(List<ProductFound> productFound) {

    return productFound.stream()
        .map(product -> StorageResponse
            .builder()
            .codeProduct(product.getCodeProduct())
            .nameProduct(product.getNameProduct())
            .priceProduct(product.getPriceProduct())
            .build())
        .collect(Collectors.toList());
  }

}
