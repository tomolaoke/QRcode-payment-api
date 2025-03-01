package com.example.qrpayment.dto;

import lombok.Data;

@Data
public class PaymentResponseDTO {
    private Long transactionId;
    private String status;
    private Double amount;
    private String currency;
    private Long userId;
    private Double userBalance;
    private Long merchantId;
    private Double merchantBalance;
    private String description;
}
