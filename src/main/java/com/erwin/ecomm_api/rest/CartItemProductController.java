package com.erwin.ecomm_api.rest;

import com.erwin.ecomm_api.model.CartItemProductDTO;
import com.erwin.ecomm_api.service.CartItemProductService;
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
@RequestMapping(value = "/api/cartItemProducts", produces = MediaType.APPLICATION_JSON_VALUE)
public class CartItemProductController {

    private final CartItemProductService cartItemProductService;

    public CartItemProductController(final CartItemProductService cartItemProductService) {
        this.cartItemProductService = cartItemProductService;
    }

    @GetMapping
    public ResponseEntity<List<CartItemProductDTO>> getAllCartItemProducts() {
        return ResponseEntity.ok(cartItemProductService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartItemProductDTO> getCartItemProduct(@PathVariable final Long id) {
        return ResponseEntity.ok(cartItemProductService.get(id));
    }

    @PostMapping
    public ResponseEntity<Long> createCartItemProduct(
            @RequestBody @Valid final CartItemProductDTO cartItemProductDTO) {
        return new ResponseEntity<>(cartItemProductService.create(cartItemProductDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCartItemProduct(@PathVariable final Long id,
            @RequestBody @Valid final CartItemProductDTO cartItemProductDTO) {
        cartItemProductService.update(id, cartItemProductDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCartItemProduct(@PathVariable final Long id) {
        cartItemProductService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
