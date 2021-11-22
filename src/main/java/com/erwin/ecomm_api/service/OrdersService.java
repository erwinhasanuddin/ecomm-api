package com.erwin.ecomm_api.service;

import com.erwin.ecomm_api.domain.Orders;
import com.erwin.ecomm_api.domain.PaymentDetails;
import com.erwin.ecomm_api.model.OrdersDTO;
import com.erwin.ecomm_api.repos.OrdersRepository;
import com.erwin.ecomm_api.repos.PaymentDetailsRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class OrdersService {

    private final OrdersRepository ordersRepository;
    private final PaymentDetailsRepository paymentDetailsRepository;

    public OrdersService(final OrdersRepository ordersRepository,
            final PaymentDetailsRepository paymentDetailsRepository) {
        this.ordersRepository = ordersRepository;
        this.paymentDetailsRepository = paymentDetailsRepository;
    }

    public List<OrdersDTO> findAll() {
        return ordersRepository.findAll()
                .stream()
                .map(orders -> mapToDTO(orders, new OrdersDTO()))
                .collect(Collectors.toList());
    }

    public OrdersDTO get(final Integer id) {
        return ordersRepository.findById(id)
                .map(orders -> mapToDTO(orders, new OrdersDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Integer create(final OrdersDTO ordersDTO) {
        final Orders orders = new Orders();
        mapToEntity(ordersDTO, orders);
        return ordersRepository.save(orders).getId();
    }

    public void update(final Integer id, final OrdersDTO ordersDTO) {
        final Orders orders = ordersRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(ordersDTO, orders);
        ordersRepository.save(orders);
    }

    public void delete(final Integer id) {
        ordersRepository.deleteById(id);
    }

    private OrdersDTO mapToDTO(final Orders orders, final OrdersDTO ordersDTO) {
        ordersDTO.setId(orders.getId());
        ordersDTO.setUserId(orders.getUserId());
        ordersDTO.setTotal(orders.getTotal());
        ordersDTO.setPaymentDetailId(orders.getPaymentDetailId());
        ordersDTO.setOrderStatus(orders.getOrderStatus());
        ordersDTO.setOneOrderOnePaymentDetails(orders.getOneOrderOnePaymentDetails() == null ? null : orders.getOneOrderOnePaymentDetails().getId());
        return ordersDTO;
    }

    private Orders mapToEntity(final OrdersDTO ordersDTO, final Orders orders) {
        orders.setUserId(ordersDTO.getUserId());
        orders.setTotal(ordersDTO.getTotal());
        orders.setPaymentDetailId(ordersDTO.getPaymentDetailId());
        orders.setOrderStatus(ordersDTO.getOrderStatus());
        if (ordersDTO.getOneOrderOnePaymentDetails() != null && (orders.getOneOrderOnePaymentDetails() == null || !orders.getOneOrderOnePaymentDetails().getId().equals(ordersDTO.getOneOrderOnePaymentDetails()))) {
            final PaymentDetails oneOrderOnePaymentDetails = paymentDetailsRepository.findById(ordersDTO.getOneOrderOnePaymentDetails())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "oneOrderOnePaymentDetails not found"));
            orders.setOneOrderOnePaymentDetails(oneOrderOnePaymentDetails);
        }
        return orders;
    }

}
