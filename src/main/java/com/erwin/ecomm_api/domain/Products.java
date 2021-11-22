package com.erwin.ecomm_api.domain;

import com.erwin.ecomm_api.model.ProductStatus;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Products {

    @Id
    @Column(nullable = false, updatable = false)
    @SequenceGenerator(
            name = "primary_sequence",
            sequenceName = "primary_sequence",
            allocationSize = 1,
            initialValue = 10000
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "primary_sequence"
    )
    private Integer id;

    @Column
    private String name;

    @Column(precision = 10, scale = 2)
    private BigDecimal price;

    @Column
    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    @Column
    private Boolean trackInventory;

    @Column
    private Boolean allowBackorders;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "one_merchants_many_products_id")
    private Merchants merchants;

    @OneToMany(mappedBy = "oneProductManyCartItemProduct")
    private Set<CartItemProduct> oneProductManyCartItemProductCartItemProducts;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "one_product_category_many_product_id")
    private ProductCategory productCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "one_product_inventory_many_product_id")
    private ProductInventory productInventory;

    @OneToMany(mappedBy = "oneProductManyOrderItems")
    private Set<OrderItems> orderItems;

    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

    @PrePersist
    public void prePersist() {
        dateCreated = OffsetDateTime.now();
        lastUpdated = dateCreated;
    }

    @PreUpdate
    public void preUpdate() {
        lastUpdated = OffsetDateTime.now();
    }

}
