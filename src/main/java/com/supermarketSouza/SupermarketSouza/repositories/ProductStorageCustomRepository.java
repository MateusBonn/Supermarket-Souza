package com.supermarketSouza.SupermarketSouza.repositories;

import com.supermarketSouza.SupermarketSouza.response.StorageResponse;
import org.springframework.data.domain.Pageable;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductStorageCustomRepository {

  List<StorageResponse> findProductInfo(String codeProduct, String nameProduct);

}
