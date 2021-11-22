package com.erwin.ecomm_api.domain;

import java.time.OffsetDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
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
public class UserAddress {

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

    @Column(length = 33)
    private String addressName;

    @Column
    private String addressLine1;

    @Column
    private String addressLine2;

    @Column(length = 50)
    private String city;

    @Column(length = 10)
    private String postcode;

    @Column
    private Integer countryCode;

    @Column(length = 21)
    private String telephone;

    @Column(length = 21)
    private String mobile;

    @Column
    private Boolean mainAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "one_user_detail_many_user_address_id")
    private UserDetail oneUserDetailManyUserAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "one_countries_many_user_address_id")
    private Countries oneCountriesManyUserAddress;

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
