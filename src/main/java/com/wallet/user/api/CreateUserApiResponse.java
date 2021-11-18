package com.wallet.user.api;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreateUserApiResponse {

    private UUID id;

    private String name;

    private String lastName;

    private Integer age;

}
