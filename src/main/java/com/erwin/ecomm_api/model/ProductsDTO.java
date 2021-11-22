package com.erwin.ecomm_api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ProductsDTO {

    private Integer id;

    @Size(max = 255)
    private String name;

    @Digits(integer = 10, fraction = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Schema(type = "string", example = "71.08")
    private BigDecimal price;

    private ProductStatus status = ProductStatus.IN_STOCK;

    private Boolean trackInventory = true;

    private Boolean allowBackorders = false;

    private Integer merchantId;

    private Integer productCategoryId;

    private Integer productInventoryId;

}
