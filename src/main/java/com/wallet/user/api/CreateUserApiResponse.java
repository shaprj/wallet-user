package com.wallet.user.api;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreateUserApiResponse {

    @NotNull(message = "Id is mandatory")
    private UUID id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Last name is mandatory")
    private String lastName;

    @NotNull(message = "Age is mandatory")
    private Integer age;

}
