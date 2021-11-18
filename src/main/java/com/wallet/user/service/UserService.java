package com.wallet.user.service;

import com.wallet.user.api.*;
import com.wallet.user.data.model.WalletUser;
import com.wallet.user.data.repo.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService implements UserServiceOperations {

    @Autowired
    private UserRepo repo;

    @Autowired
    private ModelMapper mapper;

    @Override
    public CreateUserApiResponse createUser(final CreateUserApiRequest request) {
        log.info("Begin creating user by request {}", request);
        final WalletUser user = mapper.map(request, WalletUser.class);
        user.setIsBanned(false);
        repo.save(user);
        log.info("User {} created", request);
        return mapper.map(user, CreateUserApiResponse.class);
    }

    @Override
    public BanUserApiResponse banUser(BanUserApiRequest request) {
        log.info("Begin banning user by request {}", request);
        final WalletUser user = repo.findById(request.getUserId()).orElseThrow(() ->
                new NoSuchElementException(String.format("Can't find user for id %s", request.getUserId())));
        user.setIsBanned(true);
        user.setBanReason(request.getBanReason());
        repo.save(user);
        log.info("User successfully banned by request {}", request);
        final BanUserApiResponse response = mapper.map(request, BanUserApiResponse.class);
        response.setSuccess(true);
        return response;
    }

    @Override
    public List<GetUserApiResponse> getAllUsers() {
        final List<WalletUser> userList = repo.findAll();
        return userList.stream().map(user -> mapper.map(user, GetUserApiResponse.class)).collect(Collectors.toList());
    }
}
