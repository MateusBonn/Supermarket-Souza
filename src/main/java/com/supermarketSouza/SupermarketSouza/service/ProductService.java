package com.supermarketSouza.SupermarketSouza.service;

import com.supermarketSouza.SupermarketSouza.model.ProductBoughtModel;
import com.supermarketSouza.SupermarketSouza.model.ProductToSellModel;
import com.supermarketSouza.SupermarketSouza.repositories.ProductRepository;
import com.supermarketSouza.SupermarketSouza.request.ProductBoughtDTO;
import com.supermarketSouza.SupermarketSouza.request.ProductSellDTO;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

  final ProductRepository productRepository;

  @Transactional
  public ProductBoughtModel saveProduct(ProductBoughtDTO request) {

    request.setNameProduct(request.getNameProduct().toUpperCase());
    var productBoughtModel = new ProductBoughtModel();
    productBoughtModel.setRegistrationDate(LocalDateTime.now());
    BeanUtils.copyProperties(request, productBoughtModel);
    return productRepository.save(productBoughtModel);

  }

  public ProductToSellModel saveProductSold(ProductSellDTO productSellDTO) {
    if(productSellDTO.getProductQuantitySold() == null || productSellDTO.getProductQuantitySold() == 0){
      productSellDTO.setProductQuantitySold(1L);
    }
    var productToSellModel = new ProductToSellModel();
    productToSellModel.setProductQuantityToSell(productSellDTO.getProductQuantitySold() - productToSellModel.getProductQuantityToSell());
    return null;
  }

 /* @Transactional
  public Object updateProductQuantity(ProductToSellModel productToSell, ProductDTO request) {

    var newProductQuantity = productToSell.getProductQuantityToSell() - request.getProductQuantity();

    return productRepository.save(productModel);
  }

  public Optional<ProductBoughtModel> findCodeProduct(String codeProduct) {

   return productRepository.findByCodeProduct(codeProduct);
  }*/
}
