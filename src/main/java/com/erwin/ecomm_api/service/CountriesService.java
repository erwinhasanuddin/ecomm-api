package com.erwin.ecomm_api.service;

import com.erwin.ecomm_api.domain.Countries;
import com.erwin.ecomm_api.model.CountriesDTO;
import com.erwin.ecomm_api.repos.CountriesRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class CountriesService {

    private final CountriesRepository countriesRepository;

    public CountriesService(final CountriesRepository countriesRepository) {
        this.countriesRepository = countriesRepository;
    }

    public List<CountriesDTO> findAll() {
        return countriesRepository.findAll()
                .stream()
                .map(countries -> mapToDTO(countries, new CountriesDTO()))
                .collect(Collectors.toList());
    }

    public CountriesDTO get(final Integer code) {
        return countriesRepository.findById(code)
                .map(countries -> mapToDTO(countries, new CountriesDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Integer create(final CountriesDTO countriesDTO) {
        final Countries countries = new Countries();
        mapToEntity(countriesDTO, countries);
        return countriesRepository.save(countries).getId();
    }

    public void update(final Integer code, final CountriesDTO countriesDTO) {
        final Countries countries = countriesRepository.findById(code)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(countriesDTO, countries);
        countriesRepository.save(countries);
    }

    public void delete(final Integer code) {
        countriesRepository.deleteById(code);
    }

    private CountriesDTO mapToDTO(final Countries countries, final CountriesDTO countriesDTO) {
        countriesDTO.setId(countries.getId());
        countriesDTO.setCode(countries.getCode());
        countriesDTO.setName(countries.getName());
        countriesDTO.setContinentName(countries.getContinentName());
        countriesDTO.setActiveStatus(countries.getActiveStatus());
        return countriesDTO;
    }

    private Countries mapToEntity(final CountriesDTO countriesDTO, final Countries countries) {
        countries.setCode(countriesDTO.getCode());
        countries.setName(countriesDTO.getName());
        countries.setContinentName(countriesDTO.getContinentName());
        countries.setActiveStatus(countriesDTO.getActiveStatus());
        return countries;
    }

}
