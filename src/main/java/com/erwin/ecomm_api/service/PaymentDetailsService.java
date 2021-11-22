package com.erwin.ecomm_api.service;

import com.erwin.ecomm_api.domain.PaymentDetails;
import com.erwin.ecomm_api.model.PaymentDetailsDTO;
import com.erwin.ecomm_api.repos.PaymentDetailsRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class PaymentDetailsService {

    private final PaymentDetailsRepository paymentDetailsRepository;

    public PaymentDetailsService(final PaymentDetailsRepository paymentDetailsRepository) {
        this.paymentDetailsRepository = paymentDetailsRepository;
    }

    public List<PaymentDetailsDTO> findAll() {
        return paymentDetailsRepository.findAll()
                .stream()
                .map(paymentDetails -> mapToDTO(paymentDetails, new PaymentDetailsDTO()))
                .collect(Collectors.toList());
    }

    public PaymentDetailsDTO get(final Integer id) {
        return paymentDetailsRepository.findById(id)
                .map(paymentDetails -> mapToDTO(paymentDetails, new PaymentDetailsDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Integer create(final PaymentDetailsDTO paymentDetailsDTO) {
        final PaymentDetails paymentDetails = new PaymentDetails();
        mapToEntity(paymentDetailsDTO, paymentDetails);
        return paymentDetailsRepository.save(paymentDetails).getId();
    }

    public void update(final Integer id, final PaymentDetailsDTO paymentDetailsDTO) {
        final PaymentDetails paymentDetails = paymentDetailsRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(paymentDetailsDTO, paymentDetails);
        paymentDetailsRepository.save(paymentDetails);
    }

    public void delete(final Integer id) {
        paymentDetailsRepository.deleteById(id);
    }

    private PaymentDetailsDTO mapToDTO(final PaymentDetails paymentDetails,
            final PaymentDetailsDTO paymentDetailsDTO) {
        paymentDetailsDTO.setId(paymentDetails.getId());
        paymentDetailsDTO.setAmount(paymentDetails.getAmount());
        paymentDetailsDTO.setPaymentStatus(paymentDetails.getPaymentStatus());
        return paymentDetailsDTO;
    }

    private PaymentDetails mapToEntity(final PaymentDetailsDTO paymentDetailsDTO,
            final PaymentDetails paymentDetails) {
        paymentDetails.setAmount(paymentDetailsDTO.getAmount());
        paymentDetails.setPaymentStatus(paymentDetailsDTO.getPaymentStatus());
        return paymentDetails;
    }

}
