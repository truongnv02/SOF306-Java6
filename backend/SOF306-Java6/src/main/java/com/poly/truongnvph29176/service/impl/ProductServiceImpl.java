package com.poly.truongnvph29176.service.impl;

import com.poly.truongnvph29176.dto.request.ProductRequest;
import com.poly.truongnvph29176.dto.response.ProductListResponse;
import com.poly.truongnvph29176.dto.response.ProductResponse;
import com.poly.truongnvph29176.entity.Category;
import com.poly.truongnvph29176.entity.Product;
import com.poly.truongnvph29176.exception.DataNotFoundException;
import com.poly.truongnvph29176.repository.CategoryRepository;
import com.poly.truongnvph29176.repository.ProductRepository;
import com.poly.truongnvph29176.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Page<ProductResponse> getAllProducts(Pageable pageable) {
        Page<Product> productPage = productRepository.findAll(pageable);
        return productPage.map(product -> ProductResponse
                .builder()
                .id(product.getId())
                .name(product.getName())
                .image(product.getImage())
                .price(product.getPrice())
                .createDate(product.getCreateDate())
                .available(product.getAvailable())
                .categoryId(product.getCategory().getId())
                .build());
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product createProduct(ProductRequest productRequest) throws Exception {
        Category category = categoryRepository
                .findById(productRequest.getCategoryId())
                .orElseThrow(() ->
                        new DataNotFoundException(
                                "Cannot find Category with id = " + productRequest.getCategoryId())
                );
        Product product = Product.builder()
                .name(productRequest.getName())
                .image(productRequest.getImage())
                .price(productRequest.getPrice())
                .available(productRequest.getAvailable())
                .category(category)
                .build();
        return productRepository.save(product);
    }

    @Override
    public Product getProductById(Integer id) throws Exception {
        return productRepository.findById(id).orElseThrow(() ->
                    new DataNotFoundException("Cannot find with id = " + id)
                );
    }

    @Override
    @Transactional
    public Product updateProduct(Integer id, ProductRequest productRequest) throws Exception {
        Product product = getProductById(id);
        if(product != null) {
            Category category = categoryRepository
                    .findById(productRequest.getCategoryId())
                    .orElseThrow(() ->
                                new DataNotFoundException(
                                        "Cannot find Category with id = " + productRequest.getCategoryId())
                            );
            product.setName(productRequest.getName());
            product.setImage(productRequest.getImage());
            product.setPrice(productRequest.getPrice());
            product.setAvailable(productRequest.getAvailable());
            product.setCategory(category);
            return productRepository.save(product);
        }
        return null;
    }

    @Override
    @Transactional
    public void deleteProduct(Integer id) {
        productRepository.deleteById(id);
    }

    @Override
    public int getTotalProductCount() {
        return productRepository.getTotalProductCount();
    }

    @Override
    public int getTotalProductCountByCategory(String id) throws Exception {
        return productRepository.getTotalProductCountByCategory(id);
    }
}
