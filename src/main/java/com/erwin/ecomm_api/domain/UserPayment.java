package com.erwin.ecomm_api.domain;

import com.erwin.ecomm_api.model.PaymentType;
import java.time.OffsetDateTime;
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
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class UserPayment {

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
    private Integer userId;

    @Column
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    @Column
    private String bankName;

    @Column
    private String accountName;

    @Column(length = 50)
    private String accountNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "one_user_detail_many_user_payment_id")
    private UserDetail oneUserDetailManyUserPayment;

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
