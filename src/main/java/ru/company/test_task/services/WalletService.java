package ru.company.test_task.services;

import org.springframework.http.ResponseEntity;
import ru.company.test_task.models.ErrorResponse;
import ru.company.test_task.models.Wallet;
import ru.company.test_task.models.WalletRequest;

import java.util.UUID;

public interface WalletService {
    ResponseEntity<?> editWallet(WalletRequest data);
    ResponseEntity<?> findByWalletId(UUID walletId);
}
