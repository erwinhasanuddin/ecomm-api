package com.erwin.ecomm_api.service;

import com.erwin.ecomm_api.domain.Countries;
import com.erwin.ecomm_api.domain.UserAddress;
import com.erwin.ecomm_api.domain.UserDetail;
import com.erwin.ecomm_api.model.UserAddressDTO;
import com.erwin.ecomm_api.repos.CountriesRepository;
import com.erwin.ecomm_api.repos.UserAddressRepository;
import com.erwin.ecomm_api.repos.UserDetailRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class UserAddressService {

    private final UserAddressRepository userAddressRepository;
    private final UserDetailRepository userDetailRepository;
    private final CountriesRepository countriesRepository;

    public UserAddressService(final UserAddressRepository userAddressRepository,
            final UserDetailRepository userDetailRepository,
            final CountriesRepository countriesRepository) {
        this.userAddressRepository = userAddressRepository;
        this.userDetailRepository = userDetailRepository;
        this.countriesRepository = countriesRepository;
    }

    public List<UserAddressDTO> findAll() {
        return userAddressRepository.findAll()
                .stream()
                .map(userAddress -> mapToDTO(userAddress, new UserAddressDTO()))
                .collect(Collectors.toList());
    }

    public UserAddressDTO get(final Integer id) {
        return userAddressRepository.findById(id)
                .map(userAddress -> mapToDTO(userAddress, new UserAddressDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Integer create(final UserAddressDTO userAddressDTO) {
        final UserAddress userAddress = new UserAddress();
        mapToEntity(userAddressDTO, userAddress);
        return userAddressRepository.save(userAddress).getId();
    }

    public void update(final Integer id, final UserAddressDTO userAddressDTO) {
        final UserAddress userAddress = userAddressRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(userAddressDTO, userAddress);
        userAddressRepository.save(userAddress);
    }

    public void delete(final Integer id) {
        userAddressRepository.deleteById(id);
    }

    private UserAddressDTO mapToDTO(final UserAddress userAddress,
            final UserAddressDTO userAddressDTO) {
        userAddressDTO.setId(userAddress.getId());
        userAddressDTO.setUserId(userAddress.getUserId());
        userAddressDTO.setAddressName(userAddress.getAddressName());
        userAddressDTO.setAddressLine1(userAddress.getAddressLine1());
        userAddressDTO.setAddressLine2(userAddress.getAddressLine2());
        userAddressDTO.setCity(userAddress.getCity());
        userAddressDTO.setPostcode(userAddress.getPostcode());
        userAddressDTO.setCountryCode(userAddress.getCountryCode());
        userAddressDTO.setTelephone(userAddress.getTelephone());
        userAddressDTO.setMobile(userAddress.getMobile());
        userAddressDTO.setMainAddress(userAddress.getMainAddress());
        userAddressDTO.setOneUserDetailManyUserAddress(userAddress.getOneUserDetailManyUserAddress() == null ? null : userAddress.getOneUserDetailManyUserAddress().getId());
        userAddressDTO.setOneCountriesManyUserAddress(userAddress.getOneCountriesManyUserAddress() == null ? null : userAddress.getOneCountriesManyUserAddress().getId());
        return userAddressDTO;
    }

    private UserAddress mapToEntity(final UserAddressDTO userAddressDTO,
            final UserAddress userAddress) {
        userAddress.setUserId(userAddressDTO.getUserId());
        userAddress.setAddressName(userAddressDTO.getAddressName());
        userAddress.setAddressLine1(userAddressDTO.getAddressLine1());
        userAddress.setAddressLine2(userAddressDTO.getAddressLine2());
        userAddress.setCity(userAddressDTO.getCity());
        userAddress.setPostcode(userAddressDTO.getPostcode());
        userAddress.setCountryCode(userAddressDTO.getCountryCode());
        userAddress.setTelephone(userAddressDTO.getTelephone());
        userAddress.setMobile(userAddressDTO.getMobile());
        userAddress.setMainAddress(userAddressDTO.getMainAddress());
        if (userAddressDTO.getOneUserDetailManyUserAddress() != null && (userAddress.getOneUserDetailManyUserAddress() == null || !userAddress.getOneUserDetailManyUserAddress().getId().equals(userAddressDTO.getOneUserDetailManyUserAddress()))) {
            final UserDetail oneUserDetailManyUserAddress = userDetailRepository.findById(userAddressDTO.getOneUserDetailManyUserAddress())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "oneUserDetailManyUserAddress not found"));
            userAddress.setOneUserDetailManyUserAddress(oneUserDetailManyUserAddress);
        }
        if (userAddressDTO.getOneCountriesManyUserAddress() != null && (userAddress.getOneCountriesManyUserAddress() == null || !userAddress.getOneCountriesManyUserAddress().getCode().equals(userAddressDTO.getOneCountriesManyUserAddress()))) {
            final Countries oneCountriesManyUserAddress = countriesRepository.findById(userAddressDTO.getOneCountriesManyUserAddress())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "oneCountriesManyUserAddress not found"));
            userAddress.setOneCountriesManyUserAddress(oneCountriesManyUserAddress);
        }
        return userAddress;
    }

}
