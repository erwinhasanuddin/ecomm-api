package com.erwin.ecomm_api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import javax.validation.constraints.Digits;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ShoppingSessionDTO {

    private Integer id;

    private Integer userId;

    @Digits(integer = 10, fraction = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(type = "string", example = "28.08")
    private BigDecimal total = BigDecimal.valueOf(0);

}
