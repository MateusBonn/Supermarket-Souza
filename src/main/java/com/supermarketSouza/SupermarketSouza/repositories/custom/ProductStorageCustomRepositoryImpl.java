package com.supermarketSouza.SupermarketSouza.repositories.custom;

import com.supermarketSouza.SupermarketSouza.repositories.ProductStorageCustomRepository;
import com.supermarketSouza.SupermarketSouza.repositories.utils.QueryHolder;
import com.supermarketSouza.SupermarketSouza.response.ProductFound;
import com.supermarketSouza.SupermarketSouza.repositories.utils.OrderByAndPaginationBuilder;
import com.supermarketSouza.SupermarketSouza.utils.PageFilter;
import com.supermarketSouza.SupermarketSouza.repositories.utils.SearchByBuilder;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import org.springframework.jdbc.core.namedparam.EmptySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
@RequiredArgsConstructor
public class ProductStorageCustomRepositoryImpl implements ProductStorageCustomRepository {

  private final NamedParameterJdbcTemplate jdbcTemplate;

    private Map<String, String> closingPeriodDictionary() {
      return Map.ofEntries(
          Map.entry("code", "code_product"),
          Map.entry("name", "name_product"));
    }

    public List<ProductFound> findAll(PageFilter pageFilter, List<String> searchBy) {
      QueryHolder queryHolderSearch = SearchByBuilder.buildGenericSearchBy(pageFilter.getSearch(), searchBy, closingPeriodDictionary());

      StringBuilder sql = new StringBuilder()
          .append(buildSqlFindAll())
          .append(queryHolderSearch.getSqlQuery())
          .append(OrderByAndPaginationBuilder.buildSortAndPagination(pageFilter.toPageable()));


      return null;// jdbcTemplate.query(sql.toString(), queryHolderSearch.getMapParams(), new ProductQueryObjectMapper());
    }

    public long count() {
      StringBuilder sql = new StringBuilder("SELECT COUNT(1) ")
          .append(buildFromFindAll());

      return jdbcTemplate.queryForObject(sql.toString(), new EmptySqlParameterSource(), Long.class);
    }

    private String buildSqlFindAll() {
      return new StringBuilder()
          .append(buildSelectSql())
          .append(buildFromFindAll())
          .toString();
    }

    private String buildSelectSql() {
      return new StringBuilder()
          .append("SELECT ")
          .append(" s.code_product as code, ")
          .append(" s.name_product as name, ")
          .append(" b.price_product_to_sell as price ")
          .toString();
    }

    private String buildFromFindAll() {
      return new StringBuilder()
          .append(" FROM TB_PRODUCT_STORAGE s ")
          .append(" JOIN tb_product_bought b ON s.code_product = b.code_product")
          .toString();
    }
}
