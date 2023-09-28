package com.supermarketSouza.SupermarketSouza.repositories.custom;

import com.supermarketSouza.SupermarketSouza.repositories.ProductStorageCustomRepository;
import com.supermarketSouza.SupermarketSouza.repositories.utils.OrderByAndPaginationBuilder;
import com.supermarketSouza.SupermarketSouza.repositories.utils.QueryHolder;
import com.supermarketSouza.SupermarketSouza.repositories.utils.SearchByBuilder;
import com.supermarketSouza.SupermarketSouza.response.ProductFound;
import com.supermarketSouza.SupermarketSouza.utils.PageFilter;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
@RequiredArgsConstructor
public class ProductStorageCustomRepositoryImpl implements ProductStorageCustomRepository {

  private final NamedParameterJdbcTemplate jdbcTemplate;

    private Map<String, String> closingPeriodDictionary() {
      return Map.ofEntries(
          Map.entry("code", "s.code_product"),
          Map.entry("name", "s.name_product"));
    }

    public List<ProductFound> findAll(PageFilter pageFilter, List<String> searchBy) {
      pageFilter.setOrderBy("s." + pageFilter.getOrderBy());
      QueryHolder queryHolderSearch = SearchByBuilder.buildGenericSearchBy(pageFilter.getSearch(), searchBy, closingPeriodDictionary());

      StringBuilder sql = new StringBuilder()
          .append(buildSqlFindAll())
          .append(queryHolderSearch.getSqlQuery())
          .append(OrderByAndPaginationBuilder.buildSortAndPagination(pageFilter.toPageable()));




      return jdbcTemplate.query(sql.toString(),
                                bindFilterFindAllWithSearch(queryHolderSearch.getMapParams()),
                                BeanPropertyRowMapper.newInstance(ProductFound.class));
    }

    public long count(PageFilter pageFilter, List<String> searchBy) {
      QueryHolder queryHolderSearch = SearchByBuilder.buildGenericSearchBy(pageFilter.getSearch(), searchBy, closingPeriodDictionary());


      StringBuilder sql = new StringBuilder("SELECT COUNT(*) ")
          .append(buildFromFindAll())
          .append(queryHolderSearch.getSqlQuery());

      return jdbcTemplate.queryForObject(sql.toString(), bindFilterFindAllWithSearch(queryHolderSearch.getMapParams()), Long.class);
    }

    private MapSqlParameterSource bindFilterFindAllWithSearch(Map<String, Object> mapParams) {
      var parameters = new MapSqlParameterSource();

      for (Map.Entry<String, Object> entry : mapParams.entrySet()) {
        parameters = parameters.addValue(entry.getKey(), entry.getValue());
      }

      return parameters;
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
          .append(" s.code_product as codeProduct, ")
          .append(" s.name_product as nameProduct, ")
          .append(" b.price_product_to_sell as priceProduct ")
          .toString();
    }

    private String buildFromFindAll() {
      return new StringBuilder()
          .append(" FROM tb_product_storage s ")
          .append("JOIN (")
          .append(buildMaxDateSql())
          .append(") as max_dates ")
          .append(" JOIN tb_product_bought b ON s.code_product = b.code_product ")
          .append(" AND b.registration_date = max_dates.max_date ")
          .append(" WHERE s.product_quantity > 0 ")
          .toString();
    }

    private String buildMaxDateSql() {
      return new StringBuilder()
          .append(" SELECT code_product, MAX(registration_date) as max_date ")
          .append(" FROM tb_product_bought ")
          .append(" GROUP BY code_product ")
          .toString();
    }
}
