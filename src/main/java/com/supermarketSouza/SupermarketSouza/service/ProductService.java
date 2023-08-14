package com.supermarketSouza.SupermarketSouza.service;

import com.supermarketSouza.SupermarketSouza.model.ProductModel;
import com.supermarketSouza.SupermarketSouza.repositories.ProductRepository;
import com.supermarketSouza.SupermarketSouza.request.ProductDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

  final ProductRepository productRepository;

  public ProductModel saveProduct(ProductDTO request) {

    request.setNameProduct(request.getNameProduct().toUpperCase());
    var productModel = new ProductModel();
    BeanUtils.copyProperties(request, productModel);
    return productRepository.save(productModel);

  }
}
