package com.wallet.user.api;

import lombok.*;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreateUserApiRequest {

    private String name;

    private String lastName;

    private Integer age;

}
