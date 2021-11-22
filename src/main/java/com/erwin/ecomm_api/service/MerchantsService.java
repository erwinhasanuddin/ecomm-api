package com.erwin.ecomm_api.service;

import com.erwin.ecomm_api.domain.Merchants;
import com.erwin.ecomm_api.model.MerchantsDTO;
import com.erwin.ecomm_api.repos.MerchantsRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class MerchantsService {

    private final MerchantsRepository merchantsRepository;

    public MerchantsService(final MerchantsRepository merchantsRepository) {
        this.merchantsRepository = merchantsRepository;
    }

    public List<MerchantsDTO> findAll() {
        return merchantsRepository.findAll()
                .stream()
                .map(merchants -> mapToDTO(merchants, new MerchantsDTO()))
                .collect(Collectors.toList());
    }

    public MerchantsDTO get(final Integer id) {
        return merchantsRepository.findById(id)
                .map(merchants -> mapToDTO(merchants, new MerchantsDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Integer create(final MerchantsDTO merchantsDTO) {
        final Merchants merchants = new Merchants();
        mapToEntity(merchantsDTO, merchants);
        return merchantsRepository.save(merchants).getId();
    }

    public void update(final Integer id, final MerchantsDTO merchantsDTO) {
        final Merchants merchants = merchantsRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(merchantsDTO, merchants);
        merchantsRepository.save(merchants);
    }

    public void delete(final Integer id) {
        merchantsRepository.deleteById(id);
    }

    private MerchantsDTO mapToDTO(final Merchants merchants, final MerchantsDTO merchantsDTO) {
        merchantsDTO.setId(merchants.getId());
        merchantsDTO.setCountryCode(merchants.getCountryCode());
        merchantsDTO.setMerchantName(merchants.getMerchantName());
        merchantsDTO.setActiveStatus(merchants.getActiveStatus());
        merchantsDTO.setAdminId(merchants.getAdminId());
        return merchantsDTO;
    }

    private Merchants mapToEntity(final MerchantsDTO merchantsDTO, final Merchants merchants) {
        merchants.setCountryCode(merchantsDTO.getCountryCode());
        merchants.setMerchantName(merchantsDTO.getMerchantName());
        merchants.setActiveStatus(merchantsDTO.getActiveStatus());
        merchants.setAdminId(merchantsDTO.getAdminId());
        return merchants;
    }

}
