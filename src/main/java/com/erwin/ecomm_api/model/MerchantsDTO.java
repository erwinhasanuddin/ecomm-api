package com.erwin.ecomm_api.model;

import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class MerchantsDTO {

    private Integer id;

    private Integer countryCode;

    @Size(max = 255)
    private String merchantName;

    private ActiveStatus activeStatus = ActiveStatus.ACTIVE;

    private Integer adminId;

}
