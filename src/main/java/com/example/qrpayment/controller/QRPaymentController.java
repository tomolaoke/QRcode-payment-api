package com.example.qrpayment.controller;

import com.example.qrpayment.dto.PaymentRequestDTO;
import com.example.qrpayment.dto.PaymentResponseDTO;
import com.example.qrpayment.dto.QRCodeRequestDTO;
import com.example.qrpayment.dto.QRCodeResponseDTO;
import com.example.qrpayment.service.PaymentService;
import com.example.qrpayment.service.QRCodeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class QRPaymentController {

    @Autowired
    private QRCodeService qrCodeService;

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/generate-qr")
    public ResponseEntity<QRCodeResponseDTO> generateQRCode(@Valid @RequestBody QRCodeRequestDTO requestDTO) {
        try {
            QRCodeResponseDTO responseDTO = qrCodeService.generateQRCode(requestDTO);
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate QR code: " + e.getMessage(), e);
        }
    }

    @PostMapping("/process-payment")
    public ResponseEntity<PaymentResponseDTO> processPayment(@Valid @RequestBody PaymentRequestDTO requestDTO) {
        try {
            PaymentResponseDTO responseDTO = paymentService.processPayment(requestDTO);
            return ResponseEntity.ok(responseDTO);
        } catch (Exception e) {
            throw new RuntimeException("Failed to process payment: " + e.getMessage(), e);
        }
    }
}