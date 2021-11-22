package com.erwin.ecomm_api.rest;

import com.erwin.ecomm_api.model.ProductsDTO;
import com.erwin.ecomm_api.service.ProductsService;
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
@RequestMapping(value = "/api/products", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductsController {

    private final ProductsService productsService;

    public ProductsController(final ProductsService productsService) {
        this.productsService = productsService;
    }

    @GetMapping
    public ResponseEntity<List<ProductsDTO>> getAllProducts() {
        return ResponseEntity.ok(productsService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductsDTO> getProducts(@PathVariable final Integer id) {
        return ResponseEntity.ok(productsService.get(id));
    }

    @PostMapping
    public ResponseEntity<Integer> createProducts(
            @RequestBody @Valid final ProductsDTO productsDTO) {
        return new ResponseEntity<>(productsService.create(productsDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProducts(@PathVariable final Integer id,
            @RequestBody @Valid final ProductsDTO productsDTO) {
        productsService.update(id, productsDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProducts(@PathVariable final Integer id) {
        productsService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
