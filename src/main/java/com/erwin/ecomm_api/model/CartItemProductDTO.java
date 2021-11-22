package com.erwin.ecomm_api.model;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CartItemProductDTO {

    private Long id;
    private Long cartItemId;
    private Long productId;
    private Integer oneCartItemManyCartItemProduct;
    private Integer oneProductManyCartItemProduct;

}
