package com.example.qrpayment.service;

import com.example.qrpayment.dto.PaymentRequestDTO;
import com.example.qrpayment.dto.PaymentResponseDTO;
import com.example.qrpayment.entity.Merchant;
import com.example.qrpayment.entity.Transaction;
import com.example.qrpayment.entity.User;
import com.example.qrpayment.repository.MerchantRepository;
import com.example.qrpayment.repository.TransactionRepository;
import com.example.qrpayment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Transactional
    public PaymentResponseDTO processPayment(PaymentRequestDTO requestDTO) {
        // Get transaction
        Transaction transaction = transactionRepository.findById(requestDTO.getTransactionId())
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        // Check if transaction is already processed
        if ("COMPLETED".equals(transaction.getStatus())) {
            throw new RuntimeException("Transaction already processed");
        }

        // Get user and merchant
        User user = userRepository.findById(requestDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Merchant merchant = merchantRepository.findById(transaction.getMerchantId())
                .orElseThrow(() -> new RuntimeException("Merchant not found"));

        // Check if user has enough balance
        if (user.getBalance() < transaction.getAmount()) {
            throw new RuntimeException("Insufficient balance");
        }

        // Update balances
        user.setBalance(user.getBalance() - transaction.getAmount());
        merchant.setBalance(merchant.getBalance() + transaction.getAmount());

        // Update transaction status
        transaction.setUserId(user.getId());
        transaction.setStatus("COMPLETED");

        // Save all entities
        userRepository.save(user);
        merchantRepository.save(merchant);
        transactionRepository.save(transaction);

        // Create response
        PaymentResponseDTO responseDTO = new PaymentResponseDTO();
        responseDTO.setTransactionId(transaction.getId());
        responseDTO.setStatus("SUCCESS");
        responseDTO.setAmount(transaction.getAmount());
        responseDTO.setCurrency(transaction.getCurrency());
        responseDTO.setUserId(user.getId());
        responseDTO.setUserBalance(user.getBalance());
        responseDTO.setMerchantId(merchant.getId());
        responseDTO.setMerchantBalance(merchant.getBalance());
        responseDTO.setDescription(transaction.getDescription());

        return responseDTO;
    }
}