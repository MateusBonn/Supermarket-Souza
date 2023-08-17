package com.supermarketSouza.SupermarketSouza.controller;

import com.supermarketSouza.SupermarketSouza.request.ProductBoughtDTO;
import com.supermarketSouza.SupermarketSouza.request.ProductSellDTO;
import com.supermarketSouza.SupermarketSouza.service.ProductService;
import jakarta.validation.Valid;
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
  public ResponseEntity<Object> addProduct(@RequestBody @Valid ProductBoughtDTO request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(productService.saveProduct(request));
  }

  @PostMapping("/product-sold")
  public ResponseEntity<Object> productSold(@RequestBody @Valid ProductSellDTO productSellDTO){
    return ResponseEntity.status(HttpStatus.CREATED).body(productService.saveProductSold(productSellDTO));

  }

/*  @PutMapping("/{codeProduct}")
  public ResponseEntity<Object> updateProduct(@PathVariable(value = "codeProduct") String codeProduct,
                                                  @RequestBody ProductDTO request){
    Optional<ProductBoughtModel> productModelOptional = productService.findCodeProduct(codeProduct);
    return productModelOptional.map(productModel -> ResponseEntity.status(HttpStatus.OK)
            .body(productService.updateProductQuantity(productModel, request)))
        .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found."));
  }*/

}
