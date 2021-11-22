package com.erwin.ecomm_api.service;

import com.erwin.ecomm_api.domain.Countries;
import com.erwin.ecomm_api.domain.Merchants;
import com.erwin.ecomm_api.domain.ShoppingSession;
import com.erwin.ecomm_api.domain.UserDetail;
import com.erwin.ecomm_api.model.UserDetailDTO;
import com.erwin.ecomm_api.repos.CountriesRepository;
import com.erwin.ecomm_api.repos.MerchantsRepository;
import com.erwin.ecomm_api.repos.ShoppingSessionRepository;
import com.erwin.ecomm_api.repos.UserDetailRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class UserDetailService {

    private final UserDetailRepository userDetailRepository;
    private final CountriesRepository countriesRepository;
    private final ShoppingSessionRepository shoppingSessionRepository;
    private final MerchantsRepository merchantsRepository;

    public UserDetailService(final UserDetailRepository userDetailRepository,
            final CountriesRepository countriesRepository,
            final ShoppingSessionRepository shoppingSessionRepository,
            final MerchantsRepository merchantsRepository) {
        this.userDetailRepository = userDetailRepository;
        this.countriesRepository = countriesRepository;
        this.shoppingSessionRepository = shoppingSessionRepository;
        this.merchantsRepository = merchantsRepository;
    }

    public List<UserDetailDTO> findAll() {
        return userDetailRepository.findAll()
                .stream()
                .map(userDetail -> mapToDTO(userDetail, new UserDetailDTO()))
                .collect(Collectors.toList());
    }

    public UserDetailDTO get(final Integer id) {
        return userDetailRepository.findById(id)
                .map(userDetail -> mapToDTO(userDetail, new UserDetailDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Integer create(final UserDetailDTO userDetailDTO) {
        final UserDetail userDetail = new UserDetail();
        mapToEntity(userDetailDTO, userDetail);
        return userDetailRepository.save(userDetail).getId();
    }

    public void update(final Integer id, final UserDetailDTO userDetailDTO) {
        final UserDetail userDetail = userDetailRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(userDetailDTO, userDetail);
        userDetailRepository.save(userDetail);
    }

    public void delete(final Integer id) {
        userDetailRepository.deleteById(id);
    }

    private UserDetailDTO mapToDTO(final UserDetail userDetail, final UserDetailDTO userDetailDTO) {
        userDetailDTO.setId(userDetail.getId());
        userDetailDTO.setUsername(userDetail.getUsername());
        userDetailDTO.setFullName(userDetail.getFullName());
        userDetailDTO.setEmail(userDetail.getEmail());
        userDetailDTO.setMobile(userDetail.getMobile());
        userDetailDTO.setCountryCode(userDetail.getCountryCode());
        userDetailDTO.setPassword(userDetail.getPassword());
        userDetailDTO.setActiveStatus(userDetail.getActiveStatus());
        userDetailDTO.setCountryId(userDetail.getCountry() == null ? null : userDetail.getCountry().getId());
        userDetailDTO.setShoppingSessionId(userDetail.getShoppingSession() == null ? null : userDetail.getShoppingSession().getId());
        userDetailDTO.setMerchantId(userDetail.getMerchant() == null ? null : userDetail.getMerchant().getId());
        return userDetailDTO;
    }

    private UserDetail mapToEntity(final UserDetailDTO userDetailDTO, final UserDetail userDetail) {
        userDetail.setUsername(userDetailDTO.getUsername());
        userDetail.setFullName(userDetailDTO.getFullName());
        userDetail.setEmail(userDetailDTO.getEmail());
        userDetail.setMobile(userDetailDTO.getMobile());
        userDetail.setCountryCode(userDetailDTO.getCountryCode());
        userDetail.setPassword(userDetailDTO.getPassword());
        userDetail.setActiveStatus(userDetailDTO.getActiveStatus());
        if (userDetailDTO.getCountryId() != null && (userDetail.getCountry() == null || !userDetail.getCountry().getId().equals(userDetailDTO.getCountryId()))) {
            final Countries country = countriesRepository.findById(userDetailDTO.getCountryId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "country not found"));
            userDetail.setCountry(country);
        }
        if (userDetailDTO.getShoppingSessionId() != null && (userDetail.getShoppingSession() == null || !userDetail.getShoppingSession().getId().equals(userDetailDTO.getShoppingSessionId()))) {
            final ShoppingSession shoppingSession = shoppingSessionRepository.findById(userDetailDTO.getShoppingSessionId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "userDetail not found"));
            userDetail.setShoppingSession(shoppingSession);
        }
        if (userDetailDTO.getMerchantId() != null && (userDetail.getMerchant() == null || !userDetail.getMerchant().getId().equals(userDetailDTO.getMerchantId()))) {
            final Merchants merchant = merchantsRepository.findById(userDetailDTO.getMerchantId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "userDetail not found"));
            userDetail.setMerchant(merchant);
        }
        return userDetail;
    }

}
