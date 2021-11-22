package com.erwin.ecomm_api.rest;

import com.erwin.ecomm_api.model.CartItemDTO;
import com.erwin.ecomm_api.service.CartItemService;
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
@RequestMapping(value = "/api/cartItems", produces = MediaType.APPLICATION_JSON_VALUE)
public class CartItemController {

    private final CartItemService cartItemService;

    public CartItemController(final CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @GetMapping
    public ResponseEntity<List<CartItemDTO>> getAllCartItems() {
        return ResponseEntity.ok(cartItemService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartItemDTO> getCartItem(@PathVariable final Integer id) {
        return ResponseEntity.ok(cartItemService.get(id));
    }

    @PostMapping
    public ResponseEntity<String> createCartItem(
            @RequestBody @Valid final CartItemDTO cartItemDTO) {
        //HttpStatus.NOT_ACCEPTABLE, "Quantity added greater that available stock."
        var result = cartItemService.create(cartItemDTO);
        switch (result){
            case -1:
                return new ResponseEntity<>("Quantity added greater that available stock.", HttpStatus.INTERNAL_SERVER_ERROR);
            case -2:
                return new ResponseEntity<>("Product not found.", HttpStatus.INTERNAL_SERVER_ERROR);
            case -3:
                return new ResponseEntity<>("Parameter 'userId' is not provided.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>("CREATED", HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCartItem(@PathVariable final Integer id,
            @RequestBody @Valid final CartItemDTO cartItemDTO) {
        cartItemService.update(id, cartItemDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable final Integer id) {
        cartItemService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
