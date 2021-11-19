package com.wallet.user.data.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@EqualsAndHashCode
@Getter
@Setter
@Builder(toBuilder = true)
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "wallet_user")
public class WalletUser {

    @EqualsAndHashCode.Exclude
    @Id
    @Column(updatable = false, nullable = false)
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @NotNull(message = "Id is mandatory")
    private UUID id;

    @Column(name = "name")
    @NotBlank(message = "Name is mandatory")
    private String name;

    @Column(name = "last_name")
    @NotBlank(message = "Last name is mandatory")
    private String lastName;

    @Column(name = "age")
    @NotNull(message = "Age is mandatory")
    private Integer age;

    @Column(name = "is_banned")
    @NotNull(message = "Is banned flag is mandatory")
    private Boolean isBanned;

    @Column(name = "ban_reason")
    private String banReason;
}
