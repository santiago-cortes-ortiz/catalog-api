package com.jeisson.catalog_api.domain.repository;

import com.jeisson.catalog_api.domain.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProductRepository {

    Product save(Product product);

    Optional<Product> findById(String id);

    Page<Product> findAll(Pageable pageable);

    Page<Product> findByActive(Pageable pageable);

    void deleteById(String id);

    boolean existsById(String id);

    Page<Product> findByCategory(String category, Pageable pageable);

    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Product> findByCategoryAndNameContainingIgnoreCase(String category, String name, Pageable pageable);
}
