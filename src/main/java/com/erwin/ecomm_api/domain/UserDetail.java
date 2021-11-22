package com.erwin.ecomm_api.domain;

import com.erwin.ecomm_api.model.ActiveStatus;
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
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class UserDetail {

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
    private String username;

    @Column
    private String fullName;

    @Column
    private String email;

    @Column(length = 21)
    private String mobile;

    @Column
    private Integer countryCode;

    @Column(columnDefinition = "text")
    private String password;

    @Column
    @Enumerated(EnumType.STRING)
    private ActiveStatus activeStatus;

    @OneToMany(mappedBy = "oneUserDetailManyUserAddress")
    private Set<UserAddress> oneUserDetailManyUserAddressUserAddresss;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "one_countries_many_user_detail_id")
    private Countries country;

    @OneToOne
    @JoinColumn(name = "one_user_detail_one_shopping_session_id")
    private ShoppingSession shoppingSession;

    @OneToOne
    @JoinColumn(name = "one_user_detail_one_merchant_id")
    private Merchants Merchant;

    @OneToMany(mappedBy = "oneUserDetailManyUserPayment")
    private Set<UserPayment> oneUserDetailManyUserPaymentUserPayments;

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
