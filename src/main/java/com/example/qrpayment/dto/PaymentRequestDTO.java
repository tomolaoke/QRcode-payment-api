package com.example.qrpayment.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PaymentRequestDTO {
    @NotNull(message = "Transaction ID cannot be null")
    private Long transactionId;

    @NotNull(message = "User ID cannot be null")
    private Long userId;
}