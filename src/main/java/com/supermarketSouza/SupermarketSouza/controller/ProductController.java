package com.supermarketSouza.SupermarketSouza.controller;

import com.supermarketSouza.SupermarketSouza.exception.ProductStorageException;
import com.supermarketSouza.SupermarketSouza.model.ProductBoughtModel;
import com.supermarketSouza.SupermarketSouza.request.ProductBoughtDTO;
import com.supermarketSouza.SupermarketSouza.request.ProductSellDTO;
import com.supermarketSouza.SupermarketSouza.response.StorageResponse;
import com.supermarketSouza.SupermarketSouza.service.ProductService;
import com.supermarketSouza.SupermarketSouza.utils.PageFilter;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/supermercado-souza/product")
@RequiredArgsConstructor
@CrossOrigin(origins = "https://prismatic-moonbeam-fbdcb2.netlify.app/")
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

  @GetMapping("/search")
  public ResponseEntity<Page<StorageResponse>> searchProducts(@Valid PageFilter pageFilter,
                                                              @RequestParam(value = "searchBy", required = false) List<String> searchBy) throws ProductStorageException {
    return ResponseEntity.status(HttpStatus.OK).body(productService.searchProducts(pageFilter, searchBy));
  }



}
