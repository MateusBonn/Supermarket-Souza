package com.supermarketSouza.SupermarketSouza.controller;

import com.supermarketSouza.SupermarketSouza.model.ProductBoughtModel;
import com.supermarketSouza.SupermarketSouza.request.ProductBoughtDTO;
import com.supermarketSouza.SupermarketSouza.request.ProductSellDTO;
import com.supermarketSouza.SupermarketSouza.service.ProductService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/supermercado-souza/product")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ProductController {

  private final ProductService productService;


  @PostMapping("/add-product")
  public ResponseEntity<List<ProductBoughtModel>> addProduct(@RequestBody @Valid List<ProductBoughtDTO> request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(productService.saveProduct(request));
  }

  @PostMapping("/product-sold")
  public ResponseEntity<Object> productSold(@RequestBody @Valid List<ProductSellDTO> productSellDTO){
    return ResponseEntity.status(HttpStatus.CREATED).body(productService.saveProductSold(productSellDTO));

  }

  @GetMapping("/{codeProduct}/get-price")
  public ResponseEntity<Object> getPrice(@PathVariable String codeProduct){
    return ResponseEntity.status(HttpStatus.OK).body(productService.getPrice(codeProduct));
  }

}
