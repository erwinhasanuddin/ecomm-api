package com.erwin.ecomm_api.model;

import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CountriesDTO {

    private Integer id;

    @Size(max = 255)
    private String code;

    @Size(max = 255)
    private String name;

    @Size(max = 255)
    private String continentName;

    private ActiveStatus activeStatus = ActiveStatus.ACTIVE;

}
