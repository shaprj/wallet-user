package com.wallet.user.controller;

import com.wallet.user.api.*;
import com.wallet.user.service.UserServiceOperations;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Supplier;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserServiceOperations userServiceOperations;

    @PostMapping("/user/create")
    @Operation(tags = {"User operations"}, summary = "Create new user API")
    public ResponseEntity<CreateUserApiResponse> addNewUserApi(@RequestBody final CreateUserApiRequest request) {
        return wrapOperationWith("Create user", request,
                () -> userServiceOperations.createUser(request));
    }

    @PostMapping("/user/ban")
    @Operation(tags = {"User operations"}, summary = "Ban user by id")
    public ResponseEntity<BanUserApiResponse> banUserById(@RequestBody final BanUserApiRequest request) {
        return wrapOperationWith("Ban user", request,
                () -> userServiceOperations.banUser(request));
    }

    @GetMapping("/user/all")
    @Operation(tags = {"User operations"}, summary = "Get all users")
    ResponseEntity<List<GetUserApiResponse>> getAllUsers() {
        return wrapOperationWith("Ban user", null,
                () -> userServiceOperations.getAllUsers());
    }

    private <T> ResponseEntity<T> wrapOperationWith(final String operation, final Object request, final Supplier<T> supplier) {
        String requestString = request != null ? request.toString() : "'No Params'";
        log.info("Begin {} operation for {}", operation, request);
        T response = supplier.get();
        log.info("End {} operation for {}", operation, request);
        return ResponseEntity.ok(response);
    }
}
