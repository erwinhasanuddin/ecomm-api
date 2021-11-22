package com.erwin.ecomm_api.rest;

import com.erwin.ecomm_api.model.ShoppingSessionDTO;
import com.erwin.ecomm_api.service.ShoppingSessionService;
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
@RequestMapping(value = "/api/shoppingSessions", produces = MediaType.APPLICATION_JSON_VALUE)
public class ShoppingSessionController {

    private final ShoppingSessionService shoppingSessionService;

    public ShoppingSessionController(final ShoppingSessionService shoppingSessionService) {
        this.shoppingSessionService = shoppingSessionService;
    }

    @GetMapping
    public ResponseEntity<List<ShoppingSessionDTO>> getAllShoppingSessions() {
        return ResponseEntity.ok(shoppingSessionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShoppingSessionDTO> getShoppingSession(@PathVariable final Integer id) {
        return ResponseEntity.ok(shoppingSessionService.get(id));
    }

    @PostMapping
    public ResponseEntity<Integer> createShoppingSession(
            @RequestBody @Valid final ShoppingSessionDTO shoppingSessionDTO) {
        return new ResponseEntity<>(shoppingSessionService.create(shoppingSessionDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateShoppingSession(@PathVariable final Integer id,
            @RequestBody @Valid final ShoppingSessionDTO shoppingSessionDTO) {
        shoppingSessionService.update(id, shoppingSessionDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShoppingSession(@PathVariable final Integer id) {
        shoppingSessionService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
