package com.wallet.user.api;

import lombok.*;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ErrorResponse {

    private String message;

    private String stackTrace;

    private String description;

}
