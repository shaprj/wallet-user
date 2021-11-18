package com.wallet.user.api;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BanUserApiRequest {

    private UUID userId;

    private String banReason;

}
