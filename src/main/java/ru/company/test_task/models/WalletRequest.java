package ru.company.test_task.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class WalletRequest {
    @NotNull
    private UUID walletId;
    @NotNull
    private String operationType;
    @NotNull
    @Min(1)
    private Long amount;
}
