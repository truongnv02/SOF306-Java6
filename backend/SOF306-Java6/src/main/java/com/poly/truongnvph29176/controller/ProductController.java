package com.poly.truongnvph29176.controller;

import com.poly.truongnvph29176.dto.request.CategoryRequest;
import com.poly.truongnvph29176.dto.request.ProductRequest;
import com.poly.truongnvph29176.dto.response.ProductListResponse;
import com.poly.truongnvph29176.dto.response.ProductResponse;
import com.poly.truongnvph29176.entity.Category;
import com.poly.truongnvph29176.entity.Product;
import com.poly.truongnvph29176.service.CategoryService;
import com.poly.truongnvph29176.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/products")
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;

//    @GetMapping("")
//    public ResponseEntity<?> getAllProducts(@RequestParam(defaultValue = "0") Integer page,
//                                            @RequestParam(defaultValue = "12") Integer limit) {
//        Pageable pageable = PageRequest.of(page, limit, Sort.by("id").ascending());
//        Page<ProductResponse> productPage = productService.getAllProducts(pageable);
//        // Lấy tổng số trang
//        int totalPages = productPage.getTotalPages();
//        List<ProductResponse> products = productPage.getContent();
//        return ResponseEntity.ok(ProductListResponse.builder()
//                .products(products)
//                .totalPages(totalPages)
//                .build());
//    }

    @GetMapping("")
    public ResponseEntity<?> getAllProducts(@RequestParam(defaultValue = "0") Integer page,
                                            @RequestParam(defaultValue = "12") Integer limit) {
        Pageable pageable = PageRequest.of(page, limit);
        Page<ProductResponse> productPage = productService.getAllProducts(pageable);
        List<Product> list = productService.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> getProductCount() {
        int totalCount = productService.getTotalProductCount();
        return ResponseEntity.ok(totalCount);
    }

    @GetMapping("/count/{IdCategory}")
    public ResponseEntity<?> getProductCountCategory(@PathVariable("IdCategory") String id) throws Exception {
        int totalProductCategory = productService.getTotalProductCountByCategory(id);
        return ResponseEntity.ok(totalProductCategory);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(@PathVariable("id") Integer id) {
        try {
            Product product = productService.getProductById(id);
            return ResponseEntity.ok(product);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductRequest productRequest,
                                            BindingResult result) {
        try {
            if(result.hasErrors()) {
                List<String> errorMessage = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessage);
            }else {
                Product product = productService.createProduct(productRequest);
                return ResponseEntity.ok(product);
            }
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable("id") Integer id,
                                           @Valid @RequestBody ProductRequest productRequest,
                                           BindingResult result) {
        try {
            if(result.hasErrors()) {
                List<String> errorMessage = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessage);
            }else {
                Product product = productService.updateProduct(id, productRequest);
                return ResponseEntity.ok(product);
            }
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") Integer id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok("Deleted Product with id = " + id);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
