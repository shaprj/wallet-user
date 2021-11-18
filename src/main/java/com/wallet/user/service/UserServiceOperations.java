package com.wallet.user.service;

import com.wallet.user.api.*;

import java.util.List;

public interface UserServiceOperations {

    /* Create user operation */
    CreateUserApiResponse createUser(final CreateUserApiRequest request);

    /* Ban user operation */
    BanUserApiResponse banUser(BanUserApiRequest request);

    /* Get all users */
    List<GetUserApiResponse> getAllUsers();
}
