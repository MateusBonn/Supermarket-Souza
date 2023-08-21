package com.supermarketSouza.SupermarketSouza.repositories;


import com.supermarketSouza.SupermarketSouza.model.ProductStorageModel;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductStorageRepository extends JpaRepository<ProductStorageModel, String> {

  Optional<ProductStorageModel> findByCodeProduct(String codeProduct);

}
