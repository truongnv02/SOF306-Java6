package com.poly.truongnvph29176.repository;

import com.poly.truongnvph29176.entity.Category;
import com.poly.truongnvph29176.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query("SELECT COUNT(p) FROM Product p")
    int getTotalProductCount();

    @Query("SELECT COUNT(p) FROM Product p where p.category.id=:id")
    int getTotalProductCountByCategory(String id);
}
