package com.supermarketSouza.SupermarketSouza.controller;

import com.supermarketSouza.SupermarketSouza.request.ProductDTO;
import com.supermarketSouza.SupermarketSouza.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/supermercado-souza/product")
@RequiredArgsConstructor
public class ProductController {

  private final ProductService productService;

  @PostMapping("/add-product")
  public ResponseEntity<Object> addProduct(@RequestBody ProductDTO request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(productService.saveProduct(request));
  }

}
