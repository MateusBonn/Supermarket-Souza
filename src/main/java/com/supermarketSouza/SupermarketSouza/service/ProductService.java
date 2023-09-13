package com.supermarketSouza.SupermarketSouza.service;

import com.supermarketSouza.SupermarketSouza.mapper.ProductMapper;
import com.supermarketSouza.SupermarketSouza.model.ProductBoughtModel;
import com.supermarketSouza.SupermarketSouza.repositories.custom.ProductBoughtCustomRepository;
import com.supermarketSouza.SupermarketSouza.repositories.ProductBoughtRepository;
import com.supermarketSouza.SupermarketSouza.repositories.ProductSoldRepository;
import com.supermarketSouza.SupermarketSouza.repositories.ProductStorageRepository;
import com.supermarketSouza.SupermarketSouza.request.ProductBoughtDTO;
import com.supermarketSouza.SupermarketSouza.request.ProductSellDTO;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

  final ProductBoughtRepository productBoughtRepository;
  final ProductStorageRepository productStorageRepository;
  final ProductMapper productMapper;
  final ProductSoldRepository productSoldRepository;
  final ProductBoughtCustomRepository productBoughtCustomRepository;

  @Transactional
  public List<ProductBoughtModel> saveProduct(List<ProductBoughtDTO> request) {

    return request.stream()
        .map(productBought -> {
          productBought.setNameProduct(productBought.getNameProduct().toUpperCase());

          var productBoughtModel = new ProductBoughtModel();
          productBoughtModel.setRegistrationDate(LocalDateTime.now());
          BeanUtils.copyProperties(productBought, productBoughtModel);

          var storage = productStorageRepository.findByCodeProduct(productBoughtModel.getCodeProduct());
          storage.ifPresent(productStorageModel -> productBoughtModel.setProductQuantityBought(
              productStorageModel.getProductQuantity() + productBoughtModel.getProductQuantityBought()));

          productStorageRepository.save(productMapper.toEntityProductStorage(productBoughtModel));
          productBoughtModel.setProductQuantityBought(productBought.getProductQuantityBought());
          return productBoughtRepository.save(productBoughtModel);
        })
        .collect(Collectors.toList());
  }

  public BigDecimal saveProductSold(List<ProductSellDTO> productSellDTO) {
    return productSellDTO.stream()
        .map(productSold -> productStorageRepository.findByCodeProduct(productSold.getCodeProduct())
            .map(productStorageModel ->{
              var purchaseItems = productBoughtCustomRepository.findLatestProductBought(productSold.getCodeProduct());
              productSoldRepository.save(productMapper.toEntityProductSold(productStorageModel, productSold, purchaseItems));
              purchaseItems.setProductQuantityBought(productStorageModel.getProductQuantity() - productSold.getProductQuantitySold());
              productStorageRepository.save(productMapper.toEntityProductStorage(purchaseItems));
              return purchaseItems.getPriceProductToSell().multiply(BigDecimal.valueOf(productSold.getProductQuantitySold()));
            })
            .orElse(BigDecimal.ZERO))
        .reduce(BigDecimal.ZERO,BigDecimal::add);
  }

  public Object getPrice(String codeProduct) {
   return productBoughtCustomRepository.findLatestProductBought(codeProduct).getPriceProductToSell();
  }


}
