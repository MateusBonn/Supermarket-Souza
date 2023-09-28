package com.supermarketSouza.SupermarketSouza.service;

import com.supermarketSouza.SupermarketSouza.exception.ProductStorageException;
import com.supermarketSouza.SupermarketSouza.mapper.ProductMapper;
import com.supermarketSouza.SupermarketSouza.model.ProductBoughtModel;
import com.supermarketSouza.SupermarketSouza.model.ProductSoldModel;
import com.supermarketSouza.SupermarketSouza.repositories.ProductBoughtRepository;
import com.supermarketSouza.SupermarketSouza.repositories.ProductSoldRepository;
import com.supermarketSouza.SupermarketSouza.repositories.ProductStorageRepository;
import com.supermarketSouza.SupermarketSouza.request.ProductBoughtDTO;
import com.supermarketSouza.SupermarketSouza.request.ProductSellDTO;
import com.supermarketSouza.SupermarketSouza.response.StorageResponse;
import com.supermarketSouza.SupermarketSouza.utils.PageFilter;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

  final ProductBoughtRepository productBoughtRepository;
  final ProductStorageRepository productStorageRepository;
  final ProductMapper productMapper;
  final ProductSoldRepository productSoldRepository;

  @Transactional
  public List<ProductBoughtModel> saveProduct(List<ProductBoughtDTO> request) {

    var productBoughtList =
        request.stream()
        .map(productBought -> {
          productBought.setNameProduct(productBought.getNameProduct().toUpperCase());

          var productBoughtModel = new ProductBoughtModel();
          productBoughtModel.setRegistrationDate(LocalDateTime.now());
          BeanUtils.copyProperties(productBought, productBoughtModel);

          var storage = productStorageRepository.findByCodeProduct(productBoughtModel.getCodeProduct());
          storage.ifPresent(productStorageModel -> productBoughtModel.setProductQuantityBought(
              productStorageModel.getProductQuantity() + productBoughtModel.getProductQuantityBought()));
          return productBoughtModel;
        })
        .toList();
    productStorageRepository.saveAll(productMapper.toEntityProductStorage(productBoughtList));
    productBoughtList.forEach(productBoughtModel -> {
      var productBought = request.stream()
          .filter(productBoughtDTO -> productBoughtDTO.getCodeProduct().equals(productBoughtModel.getCodeProduct()))
          .findFirst();
      productBought.ifPresent(productBoughtDTO -> productBoughtModel.setProductQuantityBought(productBoughtDTO.getProductQuantityBought()));
    });
    return productBoughtRepository.saveAll(productBoughtList);

  }

  public List<ProductSoldModel> saveProductSold(List<ProductSellDTO> productSellDTO) {
   var productSoldOptional = productSellDTO.stream().map(
           productSell -> productStorageRepository.findByCodeProduct(productSell.getCodeProduct())
           .map(productSold -> {
             productSold.setProductQuantity(productSold.getProductQuantity() - 1);
             var storage = productStorageRepository.save(productSold);
             var price = getPrice(storage.getCodeProduct());
             return productMapper.toEntityProductSold(productSold, productSell, price);
           }))
             .collect(Collectors.toList());
    return productSoldRepository.saveAll(toList(productSoldOptional));
  }

  public BigDecimal getPrice(String codeProduct) {
    try{
      return productBoughtRepository.findLatestProductBought(codeProduct).getPriceProductToSell();
    }catch (NoResultException e){
      throw new NoResultException("");
    }
  }

  private List<ProductSoldModel> toList(List<Optional<ProductSoldModel>> productSellList) {
    return productSellList.stream().map(productSoldModel -> productSoldModel.orElse(null)).collect(Collectors.toList());
  }

  public Page<StorageResponse> searchProducts(PageFilter pageFilter, List<String> searchBy) throws ProductStorageException {
    var response = productStorageRepository.findAll(pageFilter, searchBy);

    if (response.isEmpty()){
      throw new ProductStorageException("No data found" );
    }

    var count = productStorageRepository.count(pageFilter, searchBy);

    return new PageImpl<>(productMapper.toResponse(response), pageFilter.toPageable(), count);
  }
}
