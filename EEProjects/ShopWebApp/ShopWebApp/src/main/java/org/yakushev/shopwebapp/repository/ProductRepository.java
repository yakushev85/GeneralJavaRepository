package org.yakushev.shopwebapp.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.yakushev.shopwebapp.model.Product;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Long> {
}
