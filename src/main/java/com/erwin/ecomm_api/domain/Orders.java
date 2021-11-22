package com.erwin.ecomm_api.domain;

import com.erwin.ecomm_api.model.OrderStatus;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Orders {

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

    @Column(nullable = false)
    private Integer userId;

    @Column(precision = 10, scale = 2)
    private BigDecimal total;

    @Column
    private Integer paymentDetailId;

    @Column
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "oneOrderManyOrderItems")
    private Set<OrderItems> oneOrderManyOrderItemsOrderItemss;

    @OneToOne
    @JoinColumn(name = "one_order_one_payment_details_id")
    private PaymentDetails oneOrderOnePaymentDetails;

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
