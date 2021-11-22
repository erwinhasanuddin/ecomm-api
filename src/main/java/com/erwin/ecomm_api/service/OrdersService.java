package com.erwin.ecomm_api.service;

import com.erwin.ecomm_api.domain.CartItem;
import com.erwin.ecomm_api.domain.OrderItems;
import com.erwin.ecomm_api.domain.Orders;
import com.erwin.ecomm_api.domain.PaymentDetails;
import com.erwin.ecomm_api.model.OrdersDTO;
import com.erwin.ecomm_api.repos.*;

import java.math.BigDecimal;
import java.sql.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;


@Service
public class OrdersService {

    private final OrdersRepository ordersRepository;
    private final OrderItemsRepository orderItemsRepository;
    private final PaymentDetailsRepository paymentDetailsRepository;
    private final ShoppingSessionRepository shoppingSessionRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductsRepository productsRepository;
    private final ProductInventoryRepository productInventoryRepository;
    private final CartItemProductRepository cartItemProductRepository;

    public OrdersService(final OrdersRepository ordersRepository,
            final OrderItemsRepository orderItemsRepository,
            final PaymentDetailsRepository paymentDetailsRepository,
            final ShoppingSessionRepository shoppingSessionRepository,
            final CartItemRepository cartItemRepository,
            final ProductsRepository productsRepository,
            final ProductInventoryRepository productInventoryRepository,
            final CartItemProductRepository cartItemProductRepository) {
        this.ordersRepository = ordersRepository;
        this.orderItemsRepository = orderItemsRepository;
        this.paymentDetailsRepository = paymentDetailsRepository;
        this.shoppingSessionRepository = shoppingSessionRepository;
        this.cartItemRepository = cartItemRepository;
        this.productsRepository = productsRepository;
        this.productInventoryRepository = productInventoryRepository;
        this.cartItemProductRepository = cartItemProductRepository;
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

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Integer create(final OrdersDTO ordersDTO) {
        final Orders orders = new Orders();
        mapToEntity(ordersDTO, orders);

        var shoppingSession = shoppingSessionRepository.findByuserId(orders.getUserId());
        // if no shopping session data for the give user id, return with message
        if (shoppingSession == null){
            // User doesn't have shopping session.
            return -2;
        }

        // set order total
        orders.setTotal(shoppingSession.getTotal());

        var cartItems = cartItemRepository.findByshoppingSessionId(shoppingSession.getId());
        var uniqueCartItems = new ArrayList<CartItem>(new HashSet(cartItems));
        var orderItem = new OrderItems();

        for (var cartItem : uniqueCartItems) {
            // get product detail
            var productId = cartItem.getProductId();
            var product = productsRepository.findById(productId).get();
            // get item by product id
            var dbCartItems = cartItemRepository.findByproductId(productId);
            var sumQtyInCart = dbCartItems.stream().filter(o -> o.getQuantity() > 0).mapToInt(o -> o.getQuantity()).sum();

            // GOAL: Is track inventory enabled?
            // GOAL: IF YES, CHECK THE AVAILABILITY OF THE PRODUCT STOCK TO PREVENT 'NEGATIVE INVENTORY' STOCK
            if (product.getTrackInventory()){
                var productInventory = productInventoryRepository.findByproductId(cartItem.getProductId());
                if (sumQtyInCart > productInventory.getQuantity()){
                    // Quantity added greater that available stock
                    return -1;
                }
            }

            // set order item fields
            orderItem.setProductId(cartItem.getProductId());
            orderItem.setQuantity(sumQtyInCart);
            var price = product.getPrice();
            var total = price.multiply(BigDecimal.valueOf(sumQtyInCart));
            orderItem.setPrice(total);
        }

        //save order
        var savedOrder = ordersRepository.save(orders);
        var orderId = savedOrder.getId();

        //save order item
        orderItem.setOrderId(orderId);
        var savedOrderItem = orderItemsRepository.save(orderItem);

        return orderId;
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
