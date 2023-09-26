package com.supermarketSouza.SupermarketSouza.repositories.utils;

import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class QueryHolder {

  private String sqlQuery;
  private String filters;
  private List<Object> params;
  private Map<String, Object> mapParams;

  public QueryHolder() {
  }

  public QueryHolder(String sqlQuery, Map<String, Object> mapParams) {
    this.sqlQuery = sqlQuery;
    this.mapParams = mapParams;
  }

}
