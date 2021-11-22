package com.erwin.ecomm_api.service;

import com.erwin.ecomm_api.domain.OrderItems;
import com.erwin.ecomm_api.domain.Orders;
import com.erwin.ecomm_api.domain.Products;
import com.erwin.ecomm_api.model.OrderItemsDTO;
import com.erwin.ecomm_api.repos.OrderItemsRepository;
import com.erwin.ecomm_api.repos.OrdersRepository;
import com.erwin.ecomm_api.repos.ProductsRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class OrderItemsService {

    private final OrderItemsRepository orderItemsRepository;
    private final OrdersRepository ordersRepository;
    private final ProductsRepository productsRepository;

    public OrderItemsService(final OrderItemsRepository orderItemsRepository,
            final OrdersRepository ordersRepository, final ProductsRepository productsRepository) {
        this.orderItemsRepository = orderItemsRepository;
        this.ordersRepository = ordersRepository;
        this.productsRepository = productsRepository;
    }

    public List<OrderItemsDTO> findAll() {
        return orderItemsRepository.findAll()
                .stream()
                .map(orderItems -> mapToDTO(orderItems, new OrderItemsDTO()))
                .collect(Collectors.toList());
    }

    public OrderItemsDTO get(final Integer id) {
        return orderItemsRepository.findById(id)
                .map(orderItems -> mapToDTO(orderItems, new OrderItemsDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Integer create(final OrderItemsDTO orderItemsDTO) {
        final OrderItems orderItems = new OrderItems();
        mapToEntity(orderItemsDTO, orderItems);
        return orderItemsRepository.save(orderItems).getId();
    }

    public void update(final Integer id, final OrderItemsDTO orderItemsDTO) {
        final OrderItems orderItems = orderItemsRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(orderItemsDTO, orderItems);
        orderItemsRepository.save(orderItems);
    }

    public void delete(final Integer id) {
        orderItemsRepository.deleteById(id);
    }

    private OrderItemsDTO mapToDTO(final OrderItems orderItems, final OrderItemsDTO orderItemsDTO) {
        orderItemsDTO.setId(orderItems.getId());
        orderItemsDTO.setOrderId(orderItems.getOrderId());
        orderItemsDTO.setProductId(orderItems.getProductId());
        orderItemsDTO.setQuantity(orderItems.getQuantity());
        orderItemsDTO.setPrice(orderItems.getPrice());
        orderItemsDTO.setOneOrderManyOrderItems(orderItems.getOneOrderManyOrderItems() == null ? null : orderItems.getOneOrderManyOrderItems().getId());
        orderItemsDTO.setOneProductManyOrderItems(orderItems.getOneProductManyOrderItems() == null ? null : orderItems.getOneProductManyOrderItems().getId());
        return orderItemsDTO;
    }

    private OrderItems mapToEntity(final OrderItemsDTO orderItemsDTO, final OrderItems orderItems) {
        orderItems.setOrderId(orderItemsDTO.getOrderId());
        orderItems.setProductId(orderItemsDTO.getProductId());
        orderItems.setQuantity(orderItemsDTO.getQuantity());
        orderItems.setPrice(orderItemsDTO.getPrice());
        if (orderItemsDTO.getOneOrderManyOrderItems() != null && (orderItems.getOneOrderManyOrderItems() == null || !orderItems.getOneOrderManyOrderItems().getId().equals(orderItemsDTO.getOneOrderManyOrderItems()))) {
            final Orders oneOrderManyOrderItems = ordersRepository.findById(orderItemsDTO.getOneOrderManyOrderItems())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "oneOrderManyOrderItems not found"));
            orderItems.setOneOrderManyOrderItems(oneOrderManyOrderItems);
        }
        if (orderItemsDTO.getOneProductManyOrderItems() != null && (orderItems.getOneProductManyOrderItems() == null || !orderItems.getOneProductManyOrderItems().getId().equals(orderItemsDTO.getOneProductManyOrderItems()))) {
            final Products oneProductManyOrderItems = productsRepository.findById(orderItemsDTO.getOneProductManyOrderItems())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "oneProductManyOrderItems not found"));
            orderItems.setOneProductManyOrderItems(oneProductManyOrderItems);
        }
        return orderItems;
    }

}
