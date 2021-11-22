package com.erwin.ecomm_api.rest;

import com.erwin.ecomm_api.model.CountriesDTO;
import com.erwin.ecomm_api.service.CountriesService;
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
@RequestMapping(value = "/api/countries", produces = MediaType.APPLICATION_JSON_VALUE)
public class CountriesController {

    private final CountriesService countriesService;

    public CountriesController(final CountriesService countriesService) {
        this.countriesService = countriesService;
    }

    @GetMapping
    public ResponseEntity<List<CountriesDTO>> getAllCountriess() {
        return ResponseEntity.ok(countriesService.findAll());
    }

    @GetMapping("/{code}")
    public ResponseEntity<CountriesDTO> getCountries(@PathVariable final Integer code) {
        return ResponseEntity.ok(countriesService.get(code));
    }

    @PostMapping
    public ResponseEntity<Integer> createCountries(
            @RequestBody @Valid final CountriesDTO countriesDTO) {
        return new ResponseEntity<>(countriesService.create(countriesDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{code}")
    public ResponseEntity<Void> updateCountries(@PathVariable final Integer code,
            @RequestBody @Valid final CountriesDTO countriesDTO) {
        countriesService.update(code, countriesDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<Void> deleteCountries(@PathVariable final Integer code) {
        countriesService.delete(code);
        return ResponseEntity.noContent().build();
    }

}
