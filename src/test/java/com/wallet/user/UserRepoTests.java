package com.wallet.user;

import com.wallet.user.data.model.WalletUser;
import com.wallet.user.data.repo.UserRepo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Optional;
import java.util.Set;

@SpringBootTest
public class UserRepoTests {

    private Validator validator;
    @Autowired
    private UserRepo userRepo;

    @Test
    @DisplayName("Test user repo")
    void testUserRepo() {
        final WalletUser u1 = WalletUser.builder().name("alex").lastName("smith").age(30).isBanned(false).banReason("").build();
        final WalletUser u1saved = userRepo.save(u1);
        Assertions.assertThat(u1saved.getId()).isNotNull();
        Assertions.assertThat(u1saved).isEqualTo(u1);
        final Optional<WalletUser> uFromRepo = userRepo.findById(u1saved.getId());
        Assertions.assertThat(uFromRepo).isNotEmpty();
        Assertions.assertThat(uFromRepo.get()).isEqualTo(u1);
        Assertions.assertThat(uFromRepo.get()).isEqualTo(u1saved);
        userRepo.delete(u1saved);
        Assertions.assertThat(userRepo.findById(u1saved.getId())).isEmpty();
    }
}
