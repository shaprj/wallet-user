package com.wallet.user.api;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BanUserApiResponse {

    private UUID userId;

    private String banReason;

    private Boolean success;

}
