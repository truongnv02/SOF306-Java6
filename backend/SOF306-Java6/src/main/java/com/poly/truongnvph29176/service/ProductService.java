package com.poly.truongnvph29176.service;

import com.poly.truongnvph29176.dto.request.ProductRequest;
import com.poly.truongnvph29176.dto.response.ProductResponse;
import com.poly.truongnvph29176.entity.Category;
import com.poly.truongnvph29176.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    Page<ProductResponse> getAllProducts(Pageable pageable);
    List<Product> findAll();
    Product createProduct(ProductRequest productRequest) throws Exception;
    Product getProductById(Integer id) throws Exception;
    Product updateProduct(Integer id, ProductRequest productRequest) throws Exception;
    void deleteProduct(Integer id);
    int getTotalProductCount();
    int getTotalProductCountByCategory(String id) throws Exception;
}
