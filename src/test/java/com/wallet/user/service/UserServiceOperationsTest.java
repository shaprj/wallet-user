package com.wallet.user.service;

import com.wallet.user.api.*;
import com.wallet.user.data.model.WalletUser;
import com.wallet.user.data.repo.UserRepo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@SpringBootTest
class UserServiceOperationsTest {

    @Autowired
    private UserServiceOperations service;

    @Autowired
    private UserRepo repo;

    @Test
    @DisplayName("Test create user method")
    void testCreateUser() {
        final CreateUserApiRequest request = CreateUserApiRequest.builder().name("alex").lastName("smith").age(30).build();
        final CreateUserApiResponse response = service.createUser(request);
        checkEquality(request, response);
        Assertions.assertThat(response.getId()).isNotNull();
        deleteUserById(response.getId());
    }

    @Test
    @DisplayName("Test get all users method")
    void testGetAllUsers() {
        final List<CreateUserApiRequest> rList = Arrays.asList(
                CreateUserApiRequest.builder().name("anna").lastName("go").age(18).build(),
                CreateUserApiRequest.builder().name("alex").lastName("smith").age(30).build(),
                CreateUserApiRequest.builder().name("petr").lastName("brady").age(44).build());

        final List<GetUserApiResponse> resps0 = service.getAllUsers();
        final int initialSize = resps0.size();
        final CreateUserApiResponse rs1 = service.createUser(rList.get(0));
        final List<GetUserApiResponse> resps1 = service.getAllUsers();
        Assertions.assertThat(resps1.size()).isEqualTo(initialSize + 1);
        checkEquality(rList.get(0), rs1);

        final CreateUserApiResponse rs2 = service.createUser(rList.get(1));
        final List<GetUserApiResponse> resps2 = service.getAllUsers();
        Assertions.assertThat(resps2.size()).isEqualTo(initialSize + 2);

        final CreateUserApiResponse rs3 = service.createUser(rList.get(2));
        final List<GetUserApiResponse> resps3 = service.getAllUsers();
        Assertions.assertThat(resps3.size()).isEqualTo(initialSize + 3);

        resps3.stream().forEach(resp -> deleteUserById(resp.getId()));

        final List<GetUserApiResponse> respsAfterDeletion = service.getAllUsers();
        Assertions.assertThat(respsAfterDeletion).isEmpty();
    }

    @Test
    @DisplayName("Test ban user method")
    void test() {
        final List<CreateUserApiRequest> rList = Arrays.asList(
                CreateUserApiRequest.builder().name("anna").lastName("go").age(18).build(),
                CreateUserApiRequest.builder().name("alex").lastName("smith").age(30).build(),
                CreateUserApiRequest.builder().name("petr").lastName("brady").age(44).build());

        final List<CreateUserApiResponse> respList = rList.stream()
                .map(request -> service.createUser(request))
                .collect(Collectors.toList());

        final CreateUserApiResponse any = respList.get(0);
        final BanUserApiRequest banMe = BanUserApiRequest.builder().userId(any.getId()).banReason("test_reason").build();
        final BanUserApiResponse banned = service.banUser(banMe);
        Assertions.assertThat(banned).isNotNull();
        Assertions.assertThat(banned.getSuccess()).isEqualTo(true);
        final Optional<WalletUser> u = repo.findById(any.getId());
        Assertions.assertThat(u).isNotEmpty();
        Assertions.assertThat(u.get().getIsBanned()).isEqualTo(true);
        Assertions.assertThat(u.get().getBanReason()).isEqualTo(banMe.getBanReason());

        respList.stream().forEach(resp -> deleteUserById(resp.getId()));
    }

    private void checkEquality(CreateUserApiRequest request, CreateUserApiResponse response) {
        Assertions.assertThat(request.getName()).isEqualTo(response.getName());
        Assertions.assertThat(request.getLastName()).isEqualTo(response.getLastName());
        Assertions.assertThat(request.getAge()).isEqualTo(response.getAge());
    }

    private void deleteUserById(final UUID id) {
        final Optional<WalletUser> u = repo.findById(id);
        Assertions.assertThat(u).isNotEmpty();
        repo.delete(u.get());
        Assertions.assertThat(repo.findById(id)).isEmpty();
    }

}