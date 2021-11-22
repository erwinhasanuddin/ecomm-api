package com.erwin.ecomm_api.model;

import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserPaymentDTO {

    private Integer id;

    private Integer userId;

    private PaymentType paymentType;

    @Size(max = 255)
    private String bankName;

    @Size(max = 255)
    private String accountName;

    @Size(max = 50)
    private String accountNo;

    private Integer oneUserDetailManyUserPayment;

}
