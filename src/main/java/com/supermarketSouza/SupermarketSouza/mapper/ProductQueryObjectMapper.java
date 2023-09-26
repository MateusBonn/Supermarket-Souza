package com.supermarketSouza.SupermarketSouza.mapper;

import com.supermarketSouza.SupermarketSouza.response.ProductFound;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Function;

public class ProductQueryObjectMapper implements Function<ResultSet, ProductFound> {


  @Override
  public ProductFound apply(ResultSet resultSet) {
    try {
      return ProductFound.builder()
          .codeProduct(resultSet.getString("code"))
          .nameProduct(resultSet.getString("name"))
          .priceProduct(resultSet.getBigDecimal("price"))
          .build();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
