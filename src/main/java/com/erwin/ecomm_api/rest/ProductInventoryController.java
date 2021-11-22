package com.erwin.ecomm_api.rest;

import com.erwin.ecomm_api.model.ProductInventoryDTO;
import com.erwin.ecomm_api.service.ProductInventoryService;
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
@RequestMapping(value = "/api/productInventories", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductInventoryController {

    private final ProductInventoryService productInventoryService;

    public ProductInventoryController(final ProductInventoryService productInventoryService) {
        this.productInventoryService = productInventoryService;
    }

    @GetMapping
    public ResponseEntity<List<ProductInventoryDTO>> getAllProductInventorys() {
        return ResponseEntity.ok(productInventoryService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductInventoryDTO> getProductInventory(@PathVariable final Integer id) {
        return ResponseEntity.ok(productInventoryService.get(id));
    }

    @PostMapping
    public ResponseEntity<Integer> createProductInventory(
            @RequestBody @Valid final ProductInventoryDTO productInventoryDTO) {
        return new ResponseEntity<>(productInventoryService.create(productInventoryDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProductInventory(@PathVariable final Integer id,
            @RequestBody @Valid final ProductInventoryDTO productInventoryDTO) {
        productInventoryService.update(id, productInventoryDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductInventory(@PathVariable final Integer id) {
        productInventoryService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
