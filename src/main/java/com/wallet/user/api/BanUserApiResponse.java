package com.wallet.user.api;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BanUserApiResponse {

    @NotNull(message = "User id is mandatory")
    private UUID userId;

    private String banReason;

    @NotNull(message = "Success flag is mandatory")
    private Boolean success;

}
