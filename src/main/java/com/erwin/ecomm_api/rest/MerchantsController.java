package com.erwin.ecomm_api.rest;

import com.erwin.ecomm_api.model.MerchantsDTO;
import com.erwin.ecomm_api.service.MerchantsService;
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
@RequestMapping(value = "/api/merchants", produces = MediaType.APPLICATION_JSON_VALUE)
public class MerchantsController {

    private final MerchantsService merchantsService;

    public MerchantsController(final MerchantsService merchantsService) {
        this.merchantsService = merchantsService;
    }

    @GetMapping
    public ResponseEntity<List<MerchantsDTO>> getAllMerchantss() {
        return ResponseEntity.ok(merchantsService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MerchantsDTO> getMerchants(@PathVariable final Integer id) {
        return ResponseEntity.ok(merchantsService.get(id));
    }

    @PostMapping
    public ResponseEntity<Integer> createMerchants(
            @RequestBody @Valid final MerchantsDTO merchantsDTO) {
        return new ResponseEntity<>(merchantsService.create(merchantsDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateMerchants(@PathVariable final Integer id,
            @RequestBody @Valid final MerchantsDTO merchantsDTO) {
        merchantsService.update(id, merchantsDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMerchants(@PathVariable final Integer id) {
        merchantsService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
