package com.supermarketSouza.SupermarketSouza.service;

import com.supermarketSouza.SupermarketSouza.model.ProductModel;
import com.supermarketSouza.SupermarketSouza.repositories.ProductRepository;
import com.supermarketSouza.SupermarketSouza.request.ProductDTO;
import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

  final ProductRepository productRepository;

  @Transactional
  public ProductModel saveProduct(ProductDTO request) {

    request.setNameProduct(request.getNameProduct().toUpperCase());
    var productModel = new ProductModel();
    BeanUtils.copyProperties(request, productModel);
    return productRepository.save(productModel);

  }

  @Transactional
  public Object updateProductQuantity(ProductModel productModel, ProductDTO request) {

    productModel
        .setProductQuantity(productModel.getProductQuantity() - request.getProductQuantity());

    return productRepository.save(productModel);
  }

  public Optional<ProductModel> findCodeProduct(String codeProduct) {

   return productRepository.findByCodeProduct(codeProduct);
  }
}
