package com.example.qrpayment.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "merchants")
@Data
public class Merchant {
    @Id
    private Long id;
    private String name;
    private Double balance;
}