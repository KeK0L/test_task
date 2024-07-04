package ru.company.test_task.repositories;

import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.company.test_task.models.Wallet;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, UUID> {
    @Override
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Wallet> findById(UUID walletId);

    @Modifying
    @Transactional
    @Query("UPDATE Wallet t SET t.amount = t.amount + ?1 WHERE walletId = ?2")
    void depositWallet(Long amount, UUID walletId);

    @Modifying
    @Transactional
    @Query("UPDATE Wallet t SET t.amount = t.amount - ?1 WHERE walletId = ?2 AND t.amount > ?1")
    void withdrawWallet(Long amount, UUID walletId);
}
