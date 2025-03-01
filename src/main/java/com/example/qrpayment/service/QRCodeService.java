package com.example.qrpayment.service;

import com.example.qrpayment.dto.QRCodeRequestDTO;
import com.example.qrpayment.dto.QRCodeResponseDTO;
import com.example.qrpayment.entity.Merchant;
import com.example.qrpayment.entity.Transaction;
import com.example.qrpayment.repository.MerchantRepository;
import com.example.qrpayment.repository.TransactionRepository;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;

@Service
public class QRCodeService {

    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public QRCodeResponseDTO generateQRCode(QRCodeRequestDTO requestDTO) throws WriterException, IOException {
        // Print request details for debugging
        System.out.println("Request DTO: " + requestDTO);
        System.out.println("Merchant ID: " + requestDTO.getMerchantId());

        // Check if merchant exists
        Merchant merchant = merchantRepository.findById(requestDTO.getMerchantId())
                .orElseThrow(() -> new RuntimeException("Merchant not found"));

        // Create a pending transaction
        Transaction transaction = new Transaction();
        transaction.setMerchantId(requestDTO.getMerchantId());
        transaction.setAmount(requestDTO.getAmount());
        transaction.setCurrency(requestDTO.getCurrency());
        transaction.setDescription(requestDTO.getDescription());
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setStatus("PENDING");

        Transaction savedTransaction = transactionRepository.save(transaction);

        // Generate content for QR code
        String qrContent = String.format("transactionId=%d,amount=%f,currency=%s,merchantId=%d,description=%s",
                savedTransaction.getId(), requestDTO.getAmount(), requestDTO.getCurrency(),
                requestDTO.getMerchantId(), requestDTO.getDescription());

        // Generate QR code
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(qrContent, BarcodeFormat.QR_CODE, 200, 200);

        // Convert to image
        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        byte[] pngData = pngOutputStream.toByteArray();

        // Convert to Base64
        String qrCodeBase64 = Base64.getEncoder().encodeToString(pngData);

        // Create response
        QRCodeResponseDTO responseDTO = new QRCodeResponseDTO();
        responseDTO.setQrCodeImage(qrCodeBase64);
        responseDTO.setTransactionId(savedTransaction.getId());
        responseDTO.setAmount(requestDTO.getAmount());
        responseDTO.setCurrency(requestDTO.getCurrency());
        responseDTO.setMerchantId(requestDTO.getMerchantId());
        responseDTO.setDescription(requestDTO.getDescription());

        return responseDTO;
    }
}