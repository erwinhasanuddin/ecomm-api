package com.erwin.ecomm_api.rest;

import com.erwin.ecomm_api.model.ProductCategoryDTO;
import com.erwin.ecomm_api.service.ProductCategoryService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/productCategories", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductCategoryController {

    private final ProductCategoryService productCategoryService;

    public ProductCategoryController(final ProductCategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }

    @GetMapping
    public ResponseEntity<List<ProductCategoryDTO>> getAllProductCategorys() {
        return ResponseEntity.ok(productCategoryService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductCategoryDTO> getProductCategory(@PathVariable final Integer id) {
        return ResponseEntity.ok(productCategoryService.get(id));
    }

    @PostMapping
    public ResponseEntity<Integer> createProductCategory(
            @RequestBody @Valid final ProductCategoryDTO productCategoryDTO) {
        return new ResponseEntity<>(productCategoryService.create(productCategoryDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProductCategory(@PathVariable final Integer id,
            @RequestBody @Valid final ProductCategoryDTO productCategoryDTO) {
        productCategoryService.update(id, productCategoryDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductCategory(@PathVariable final Integer id) {
        productCategoryService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
