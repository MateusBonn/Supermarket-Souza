package com.supermarketSouza.SupermarketSouza.utils;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;

/**
 * Default fields to request a page.
 *
 * @author Mateus Bonn
 */
public class PageFilter {

  private static final int DEFAULT_PAGE_INDEX = 1;
  private static final int DEFAULT_ITEMS_PER_PAGE = 50;
  private static final String DEFAULT_SEARCH = "";
  public static final String REGEX_ORDER_BY = "^(\\w+(\\+|\\s)(asc|desc)(,\\w+(\\+|\\s)(asc|desc))*)*$";

  /**
   * Define how much items will be retrieved in each page.
   * Min value = 1
   * Default value = 50
   */
  @Min(value = 1, message = "Items per page must be greater than zero.")
  @Max(value = 100, message = "Items per page must be less or equal to 100.")
  private Integer itemsPerPage;
  /**
   * Define what page will be retrieved, first page is 1.
   * Min value = 1
   * Default value = 1
   */
  @Min(value = 1, message = "Page index must be greater than zero.")
  private Integer pageIndex;

  /**
   * Define what the oder will be applied to sort the consult,
   * should be used with the following format
   * field1+asc,field2+desc...
   * Default value = Unsorted
   */
  @Pattern(
      regexp = REGEX_ORDER_BY,
      message = "Order by do not respect the pattern field1+asc,field2+desc...")
  private String orderBy;

  /**
   * Will be used to filter search by textual mode.
   * Default value = ""
   */
  private String search;

  public PageFilter() {
    itemsPerPage = DEFAULT_ITEMS_PER_PAGE;
    pageIndex = DEFAULT_PAGE_INDEX;
    search = DEFAULT_SEARCH;
  }

  public Integer getItemsPerPage() {
    return itemsPerPage;
  }

  public void setItemsPerPage(Integer itemsPerPage) {
    this.itemsPerPage = itemsPerPage;
  }

  public Integer getPageIndex() {
    return pageIndex;
  }

  public void setPageIndex(Integer pageIndex) {
    this.pageIndex = pageIndex;
  }

  public String getOrderBy() {
    return orderBy;
  }

  public void setOrderBy(String orderBy) {
    this.orderBy = orderBy;
  }

  public String getSearch() {
    return search;
  }

  public void setSearch(String search) {
    this.search = search;
  }

  public Pageable toPageable() {
    return Optional.ofNullable(orderBy)
        .map(this::buildSort)
        .map(sort -> PageRequest.of(pageIndex - 1, itemsPerPage, sort))
        .orElseGet(() -> PageRequest.of(pageIndex - 1, itemsPerPage));
  }

  private Sort buildSort(String sort) {
    if (sort == null || sort.trim().length() == 0) {
      return Sort.unsorted();
    }
    List<Order> orderList =
        Arrays.stream(sort.split(","))
            .map(
                element -> {
                  String[] split = element.split("\\s|\\+");
                  return new Order(Direction.valueOf(split[1].toUpperCase()), split[0]);
                })
            .collect(Collectors.toList());

    return Sort.by(orderList);
  }
}

