package com.wallet.user.data.repo;

import com.wallet.user.data.model.WalletUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepo extends JpaRepository<WalletUser, UUID> {
}
