package com.erwin.ecomm_api.model;

import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ProductCategoryDTO {

    private Integer id;

    @Size(max = 255)
    private String name;

    private ActiveStatus activeStatus = ActiveStatus.ACTIVE;

}
