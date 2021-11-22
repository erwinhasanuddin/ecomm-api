package com.erwin.ecomm_api.rest;

import com.erwin.ecomm_api.model.PaymentDetailsDTO;
import com.erwin.ecomm_api.service.PaymentDetailsService;
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
@RequestMapping(value = "/api/paymentDetailss", produces = MediaType.APPLICATION_JSON_VALUE)
public class PaymentDetailsController {

    private final PaymentDetailsService paymentDetailsService;

    public PaymentDetailsController(final PaymentDetailsService paymentDetailsService) {
        this.paymentDetailsService = paymentDetailsService;
    }

    @GetMapping
    public ResponseEntity<List<PaymentDetailsDTO>> getAllPaymentDetailss() {
        return ResponseEntity.ok(paymentDetailsService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDetailsDTO> getPaymentDetails(@PathVariable final Integer id) {
        return ResponseEntity.ok(paymentDetailsService.get(id));
    }

    @PostMapping
    public ResponseEntity<Integer> createPaymentDetails(
            @RequestBody @Valid final PaymentDetailsDTO paymentDetailsDTO) {
        return new ResponseEntity<>(paymentDetailsService.create(paymentDetailsDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePaymentDetails(@PathVariable final Integer id,
            @RequestBody @Valid final PaymentDetailsDTO paymentDetailsDTO) {
        paymentDetailsService.update(id, paymentDetailsDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaymentDetails(@PathVariable final Integer id) {
        paymentDetailsService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
