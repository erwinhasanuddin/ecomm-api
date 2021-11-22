package com.erwin.ecomm_api.model;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CartItemDTO {

    private Integer id;
    private Integer shoppingSessionId;
    private Integer productId;
    private Integer quantity;
    private Integer oneShoppingSessionManyCartItem;

    private Integer userId;

}
