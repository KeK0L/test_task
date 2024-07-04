package ru.company.test_task.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.company.test_task.models.ErrorResponse;
import ru.company.test_task.models.Wallet;
import ru.company.test_task.models.WalletRequest;
import ru.company.test_task.repositories.WalletRepository;
import ru.company.test_task.services.WalletService;

import java.util.UUID;

@Service
@AllArgsConstructor
public class WalletServiceImpl implements WalletService {
    private final WalletRepository repository;

    @Override
    @Transactional
    public ResponseEntity<?> editWallet(WalletRequest data) {
        Wallet wallet = repository.findById(data.getWalletId()).orElse(null);
        if (wallet != null) {
            if (data.getOperationType().equals("DEPOSIT")) {
                wallet.setAmount(wallet.getAmount() + data.getAmount());
            } else if (data.getOperationType().equals("WITHDRAW")) {
                if (data.getAmount() > wallet.getAmount()) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse(403, "The balance is less than the withdrawal amount"));
                }
                wallet.setAmount(wallet.getAmount() - data.getAmount());
            }
            repository.save(wallet);
            return ResponseEntity.ok(wallet);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse(403, "There is no such UUID"));
    }

    @Override
    @Transactional
    public ResponseEntity<?> findByWalletId(UUID walletId) {
        Wallet wallet = repository.findById(walletId).orElse(null);
        if (wallet == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse(403, "There is no such UUID"));
        } else {
            return ResponseEntity.ok(wallet);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> editWallet2(WalletRequest data) {
        Wallet wallet = repository.findById(data.getWalletId()).orElse(null);
        if (wallet != null) {
            if (data.getOperationType().equals("DEPOSIT")) {
                repository.depositWallet(data.getAmount(), wallet.getWalletId());
                wallet.setAmount(wallet.getAmount() + data.getAmount());
            } else if (data.getOperationType().equals("WITHDRAW")) {
                if (data.getAmount() > wallet.getAmount()) {
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse(403, "The balance is less than the withdrawal amount"));
                }
                repository.withdrawWallet(data.getAmount(), wallet.getWalletId());
                wallet.setAmount(wallet.getAmount() - data.getAmount());
            }
            return ResponseEntity.ok(wallet);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse(403, "There is no such UUID"));
    }

}
