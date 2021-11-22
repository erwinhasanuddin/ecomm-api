package com.erwin.ecomm_api.model;

import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserDetailDTO {

    private Integer id;

    @Size(max = 255)
    private String username;

    @Size(max = 255)
    private String fullName;

    @Size(max = 255)
    private String email;

    @Size(max = 21)
    private String mobile;

    private Integer countryCode;

    private String password;

    private ActiveStatus activeStatus = ActiveStatus.ACTIVE;

    private Integer countryId;
    private CountriesDTO country;

    private Integer shoppingSessionId;

    private Integer merchantId;

}
