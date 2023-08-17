package com.supermarketSouza.SupermarketSouza.repositories;

import com.supermarketSouza.SupermarketSouza.model.ProductBoughtModel;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductBoughtModel, UUID> {

  Optional<ProductBoughtModel> findByCodeProduct(String codeProduct);
}
