package com.erwin.ecomm_api.model;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ProductInventoryDTO {

    private Integer id;
    private Integer productId;
    private Integer quantity;

}
