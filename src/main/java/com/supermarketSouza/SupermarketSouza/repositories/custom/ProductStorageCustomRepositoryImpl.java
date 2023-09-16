package com.supermarketSouza.SupermarketSouza.repositories.custom;

import com.supermarketSouza.SupermarketSouza.repositories.ProductStorageCustomRepository;
import com.supermarketSouza.SupermarketSouza.response.StorageResponse;
import java.math.BigDecimal;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class ProductStorageCustomRepositoryImpl implements ProductStorageCustomRepository {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public List<StorageResponse> findProductInfo(String codeProduct, String nameProduct) {
    String jpql = "SELECT s.codeProduct, s.nameProduct, b.priceProductToSell "
        +"FROM ProductStorageModel s "
        +"JOIN ProductBoughtModel b ON s.codeProduct = b.codeProduct "
        +"WHERE (:codeProduct IS NULL OR s.codeProduct LIKE CONCAT('%', :codeProduct, '%')) "
        +"AND (:nameProduct IS NULL OR s.nameProduct LIKE CONCAT('%', :nameProduct, '%'))";

    TypedQuery<Object[]> query = entityManager.createQuery(jpql, Object[].class)
        .setParameter("codeProduct", codeProduct)
        .setParameter("nameProduct", nameProduct);




    return  query.getResultList().stream()
            .map(row -> StorageResponse.builder()
              .codeProduct((String) row[0])
              .nameProduct((String) row[1])
              .priceProduct((BigDecimal) row[2])
              .build()).collect(Collectors.toList());

  }
}
