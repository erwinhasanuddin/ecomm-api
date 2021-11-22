package com.erwin.ecomm_api.repos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

public class ShoppingRepositoryImpl implements ShoppingRepository {
    @Autowired
    @Lazy
    ShoppingRepository shoppingRepository;
    @Autowired
    @Lazy
    CartItemRepository cartItemRepository;

    public void getShoppingSessionByUserId() {

    }
}
