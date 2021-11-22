package com.erwin.ecomm_api.rest;

import com.erwin.ecomm_api.model.UserPaymentDTO;
import com.erwin.ecomm_api.service.UserPaymentService;
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
@RequestMapping(value = "/api/userPayments", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserPaymentController {

    private final UserPaymentService userPaymentService;

    public UserPaymentController(final UserPaymentService userPaymentService) {
        this.userPaymentService = userPaymentService;
    }

    @GetMapping
    public ResponseEntity<List<UserPaymentDTO>> getAllUserPayments() {
        return ResponseEntity.ok(userPaymentService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserPaymentDTO> getUserPayment(@PathVariable final Integer id) {
        return ResponseEntity.ok(userPaymentService.get(id));
    }

    @PostMapping
    public ResponseEntity<Integer> createUserPayment(
            @RequestBody @Valid final UserPaymentDTO userPaymentDTO) {
        return new ResponseEntity<>(userPaymentService.create(userPaymentDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUserPayment(@PathVariable final Integer id,
            @RequestBody @Valid final UserPaymentDTO userPaymentDTO) {
        userPaymentService.update(id, userPaymentDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserPayment(@PathVariable final Integer id) {
        userPaymentService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
