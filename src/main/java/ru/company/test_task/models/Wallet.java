package ru.company.test_task.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
//import org.hibernate.annotations.DynamicUpdate;
//import org.hibernate.annotations.OptimisticLockType;
//import org.hibernate.annotations.OptimisticLocking;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "wallets")
//@DynamicUpdate
//@OptimisticLocking(type = OptimisticLockType.VERSION)
public class Wallet {
    @Id
    @GeneratedValue(generator = "UUIDGenerator")
    @NonNull
    private UUID walletId;
    @NonNull
    private Long amount;
//    @Version
//    private Long version;
}
