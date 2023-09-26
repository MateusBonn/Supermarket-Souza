package com.supermarketSouza.SupermarketSouza.repositories.utils;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public final class SearchByBuilder {

  private SearchByBuilder() {

  }

  public static QueryHolder buildGenericSearchBy(final String search,
                                                 final List<String> searchBy,
                                                 final Map<String, String> mapColumn) {
    return buildGenericSearchBy(search, searchBy, mapColumn, true);
  }

  public static QueryHolder buildGenericSearchBy(final String search,
                                                 final List<String> searchBy,
                                                 final Map<String, String> mapColumn,
                                                 final boolean startWithAnd) {
    StringBuilder sql = new StringBuilder();
    Map<String, Object> mapParams = new HashMap<>();
    List<String> filteredSearchBy = Optional.ofNullable(searchBy)
        .map(list -> list.stream().filter(mapColumn::containsKey).collect(Collectors.toList()))
        .orElse(Collections.emptyList());
    int index = 0;

    if (StringUtils.hasText(search) && !CollectionUtils.isEmpty(filteredSearchBy)) {
      sql.append(startWithAnd ? " and (" : " (");
      for (String column : filteredSearchBy) {
        boolean isLastColumn = filteredSearchBy.size() - index == 1;

        sql.append("upper(")
            .append(mapColumn.get(column))
            .append("::text) like '%' || upper(:search) || '%'")
            .append(isLastColumn ? " ) " : " or ");
        index++;
      }
      mapParams.put("search", search);
    }

    return new QueryHolder(sql.toString(), mapParams);
  }
}
