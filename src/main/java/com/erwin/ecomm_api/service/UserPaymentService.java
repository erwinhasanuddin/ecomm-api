package com.erwin.ecomm_api.service;

import com.erwin.ecomm_api.domain.UserDetail;
import com.erwin.ecomm_api.domain.UserPayment;
import com.erwin.ecomm_api.model.UserPaymentDTO;
import com.erwin.ecomm_api.repos.UserDetailRepository;
import com.erwin.ecomm_api.repos.UserPaymentRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class UserPaymentService {

    private final UserPaymentRepository userPaymentRepository;
    private final UserDetailRepository userDetailRepository;

    public UserPaymentService(final UserPaymentRepository userPaymentRepository,
            final UserDetailRepository userDetailRepository) {
        this.userPaymentRepository = userPaymentRepository;
        this.userDetailRepository = userDetailRepository;
    }

    public List<UserPaymentDTO> findAll() {
        return userPaymentRepository.findAll()
                .stream()
                .map(userPayment -> mapToDTO(userPayment, new UserPaymentDTO()))
                .collect(Collectors.toList());
    }

    public UserPaymentDTO get(final Integer id) {
        return userPaymentRepository.findById(id)
                .map(userPayment -> mapToDTO(userPayment, new UserPaymentDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Integer create(final UserPaymentDTO userPaymentDTO) {
        final UserPayment userPayment = new UserPayment();
        mapToEntity(userPaymentDTO, userPayment);
        return userPaymentRepository.save(userPayment).getId();
    }

    public void update(final Integer id, final UserPaymentDTO userPaymentDTO) {
        final UserPayment userPayment = userPaymentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(userPaymentDTO, userPayment);
        userPaymentRepository.save(userPayment);
    }

    public void delete(final Integer id) {
        userPaymentRepository.deleteById(id);
    }

    private UserPaymentDTO mapToDTO(final UserPayment userPayment,
            final UserPaymentDTO userPaymentDTO) {
        userPaymentDTO.setId(userPayment.getId());
        userPaymentDTO.setUserId(userPayment.getUserId());
        userPaymentDTO.setPaymentType(userPayment.getPaymentType());
        userPaymentDTO.setBankName(userPayment.getBankName());
        userPaymentDTO.setAccountName(userPayment.getAccountName());
        userPaymentDTO.setAccountNo(userPayment.getAccountNo());
        userPaymentDTO.setOneUserDetailManyUserPayment(userPayment.getOneUserDetailManyUserPayment() == null ? null : userPayment.getOneUserDetailManyUserPayment().getId());
        return userPaymentDTO;
    }

    private UserPayment mapToEntity(final UserPaymentDTO userPaymentDTO,
            final UserPayment userPayment) {
        userPayment.setUserId(userPaymentDTO.getUserId());
        userPayment.setPaymentType(userPaymentDTO.getPaymentType());
        userPayment.setBankName(userPaymentDTO.getBankName());
        userPayment.setAccountName(userPaymentDTO.getAccountName());
        userPayment.setAccountNo(userPaymentDTO.getAccountNo());
        if (userPaymentDTO.getOneUserDetailManyUserPayment() != null && (userPayment.getOneUserDetailManyUserPayment() == null || !userPayment.getOneUserDetailManyUserPayment().getId().equals(userPaymentDTO.getOneUserDetailManyUserPayment()))) {
            final UserDetail oneUserDetailManyUserPayment = userDetailRepository.findById(userPaymentDTO.getOneUserDetailManyUserPayment())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "oneUserDetailManyUserPayment not found"));
            userPayment.setOneUserDetailManyUserPayment(oneUserDetailManyUserPayment);
        }
        return userPayment;
    }

}
