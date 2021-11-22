package com.erwin.ecomm_api.model;

import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserAddressDTO {

    private Integer id;

    private Integer userId;

    @Size(max = 33)
    private String addressName;

    @Size(max = 255)
    private String addressLine1;

    @Size(max = 255)
    private String addressLine2;

    @Size(max = 50)
    private String city;

    @Size(max = 10)
    private String postcode;

    private Integer countryCode;

    @Size(max = 21)
    private String telephone;

    @Size(max = 21)
    private String mobile;

    private Boolean mainAddress;

    private Integer oneUserDetailManyUserAddress;

    private Integer oneCountriesManyUserAddress;

}
