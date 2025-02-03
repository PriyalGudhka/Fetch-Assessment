package com.fetch.assessment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "receipts")
public class Receipts {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "retailer")
    private String retailer;

    @Column(name = "purchase_date")
    private String purchaseDate;

    @Column(name = "purchase_time")
    private String purchaseTime;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "receipt")
    private List<Items> items;

    @Column(name = "total")
    private String total;
}
