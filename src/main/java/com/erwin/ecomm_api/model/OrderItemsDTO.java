package com.erwin.ecomm_api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import javax.validation.constraints.Digits;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class OrderItemsDTO {

    private Integer id;

    private Integer orderId;

    private Integer productId;

    private Integer quantity;

    @Digits(integer = 10, fraction = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(type = "string", example = "71.08")
    private BigDecimal price;

    private Integer oneOrderManyOrderItems;

    private Integer oneProductManyOrderItems;

}
