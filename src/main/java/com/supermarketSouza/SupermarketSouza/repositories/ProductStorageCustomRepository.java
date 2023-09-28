package com.supermarketSouza.SupermarketSouza.repositories;

import com.supermarketSouza.SupermarketSouza.response.ProductFound;
import com.supermarketSouza.SupermarketSouza.utils.PageFilter;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductStorageCustomRepository {

  List<ProductFound> findAll(PageFilter pageFilter, List<String> searchBy);

  long count(PageFilter pageFilter, List<String> searchBy);

}
