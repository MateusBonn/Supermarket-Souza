package com.supermarketSouza.SupermarketSouza.repositories;


import com.supermarketSouza.SupermarketSouza.model.ProductBoughtModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductBoughtCustomRepository{

  @PersistenceContext
  private EntityManager entityManager;

  public ProductBoughtModel findLatestProductBought(String codeProduct) {
    String sql = "SELECT p "
        + " FROM ProductBoughtModel p "
        + " WHERE p.codeProduct  = :codeProduct "
        + " ORDER BY p.registrationDate DESC ";


    TypedQuery<ProductBoughtModel> query = entityManager.createQuery(sql, ProductBoughtModel.class);
    query.setParameter("codeProduct", codeProduct);
    query.setMaxResults(1); // Limit to one result

    return query.getSingleResult();
  }

}
