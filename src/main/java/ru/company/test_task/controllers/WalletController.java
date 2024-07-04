package ru.company.test_task.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.company.test_task.models.ErrorResponse;
import ru.company.test_task.models.WalletRequest;
import ru.company.test_task.services.WalletService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class WalletController {
    private final WalletService service;

    @PostMapping("wallet")
    public ResponseEntity<?> editWallet(@Valid @RequestBody WalletRequest data, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse(403, "Invalid JSON"));
        }
        return service.editWallet2(data);
    }

    @GetMapping("/wallets/{walletId}")
    public ResponseEntity<?> editWallet(@PathVariable UUID walletId) {
        return service.findByWalletId(walletId);
    }
}
