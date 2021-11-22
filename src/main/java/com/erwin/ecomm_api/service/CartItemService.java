package com.erwin.ecomm_api.service;

import com.erwin.ecomm_api.domain.CartItem;
import com.erwin.ecomm_api.domain.CartItemProduct;
import com.erwin.ecomm_api.domain.ShoppingSession;
import com.erwin.ecomm_api.model.CartItemDTO;
import com.erwin.ecomm_api.repos.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;


@Service
public class CartItemService {

    private final CartItemRepository cartItemRepository;
    private final ShoppingSessionRepository shoppingSessionRepository;
    private final ProductsRepository productsRepository;
    private final ProductInventoryRepository productInventoryRepository;
    private final CartItemProductRepository cartItemProductRepository;

    public CartItemService(final CartItemRepository cartItemRepository,
            final ShoppingSessionRepository shoppingSessionRepository,
            final ProductsRepository productsRepository,
            final ProductInventoryRepository productInventoryRepository,
            final CartItemProductRepository cartItemProductRepository) {
        this.cartItemRepository = cartItemRepository;
        this.shoppingSessionRepository = shoppingSessionRepository;
        this.productsRepository = productsRepository;
        this.productInventoryRepository = productInventoryRepository;
        this.cartItemProductRepository = cartItemProductRepository;
    }

    public List<CartItemDTO> findAll() {
        return cartItemRepository.findAll()
                .stream()
                .map(cartItem -> mapToDTO(cartItem, new CartItemDTO()))
                .collect(Collectors.toList());
    }

    public CartItemDTO get(final Integer id) {
        return cartItemRepository.findById(id)
                .map(cartItem -> mapToDTO(cartItem, new CartItemDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Integer create(final CartItemDTO cartItemDTO) {
        final CartItem cartItem = new CartItem();
        mapToEntity(cartItemDTO, cartItem);

        // calculate total price current product
        var productId = cartItem.getProductId();
        var optProduct = productsRepository.findById(productId);
        // is the product exists
        if (optProduct.isEmpty()){
            // Product not found
            return -2;
        }
        var product = optProduct.get();
        var newQty = cartItem.getQuantity();
        var newPrice = product.getPrice();

        // Is the product available in the cart item?
        var dbCartItems = cartItemRepository.findByproductId(cartItem.getProductId());
        if (dbCartItems != null){
            // product is available in cart item
            // add new qty with db qty
            var sumQtyInCart = dbCartItems.stream().filter(o -> o.getQuantity() > 0).mapToInt(o -> o.getQuantity()).sum();
            newQty = newQty + sumQtyInCart;
        }

        // GOAL: Is track inventory enabled?
        // GOAL: IF YES, CHECK THE AVAILABILITY OF THE PRODUCT STOCK TO PREVENT 'NEGATIVE INVENTORY' STOCK
        if (product.getTrackInventory()){
            var productInventory = productInventoryRepository.findByproductId(cartItem.getProductId());
            if (newQty > productInventory.getQuantity()){
                // Quantity added greater that available stock
                return -1;
            }
        }

        // calculate total price
        var total = newPrice.multiply(BigDecimal.valueOf(newQty));

        // Is the shopping session available?
        var shoppingSession = shoppingSessionRepository.findByuserId(cartItemDTO.getUserId());
        if (shoppingSession != null) {
            // get existing data
            var existingTotal = shoppingSession.getTotal();
            // calculate total
            total = total.add(existingTotal);
        } else {
            shoppingSession = new ShoppingSession();
        }

        // set shopping session fields
        shoppingSession.setUserId(cartItemDTO.getUserId());
        shoppingSession.setTotal(total);
        // save shopping session
        var shoppingSessionId = shoppingSessionRepository.save(shoppingSession).getId();
        // save cart item
        cartItem.setShoppingSessionId(shoppingSessionId);
        var savedCartItem = cartItemRepository.save(cartItem);
        // save cart item product
        var cartItemProduct = new CartItemProduct();
        cartItemProduct.setProductId(Long.valueOf(cartItem.getProductId()));
        cartItemProduct.setCartItemId(Long.valueOf(savedCartItem.getId()));
        cartItemProductRepository.save(cartItemProduct);

        return savedCartItem.getId();
    }

    public void update(final Integer id, final CartItemDTO cartItemDTO) {
        final CartItem cartItem = cartItemRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(cartItemDTO, cartItem);
        cartItemRepository.save(cartItem);
    }

    public void delete(final Integer id) {
        cartItemRepository.deleteById(id);
    }

    private CartItemDTO mapToDTO(final CartItem cartItem, final CartItemDTO cartItemDTO) {
        cartItemDTO.setId(cartItem.getId());
        cartItemDTO.setShoppingSessionId(cartItem.getShoppingSessionId());
        cartItemDTO.setProductId(cartItem.getProductId());
        cartItemDTO.setQuantity(cartItem.getQuantity());
        cartItemDTO.setOneShoppingSessionManyCartItem(cartItem.getOneShoppingSessionManyCartItem() == null ? null : cartItem.getOneShoppingSessionManyCartItem().getId());
        return cartItemDTO;
    }

    private CartItem mapToEntity(final CartItemDTO cartItemDTO, final CartItem cartItem) {
        cartItem.setShoppingSessionId(cartItemDTO.getShoppingSessionId());
        cartItem.setProductId(cartItemDTO.getProductId());
        cartItem.setQuantity(cartItemDTO.getQuantity());
        if (cartItemDTO.getOneShoppingSessionManyCartItem() != null && (cartItem.getOneShoppingSessionManyCartItem() == null || !cartItem.getOneShoppingSessionManyCartItem().getId().equals(cartItemDTO.getOneShoppingSessionManyCartItem()))) {
            final ShoppingSession oneShoppingSessionManyCartItem = shoppingSessionRepository.findById(cartItemDTO.getOneShoppingSessionManyCartItem())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "oneShoppingSessionManyCartItem not found"));
            cartItem.setOneShoppingSessionManyCartItem(oneShoppingSessionManyCartItem);
        }
        return cartItem;
    }

}
