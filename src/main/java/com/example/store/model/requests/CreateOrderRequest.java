package com.example.store.model.requests;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderRequest {

    @NotNull
    private String email;

    @NotNull
    private LocalDateTime creationDate;

    @NotEmpty
    Set<Long> products;
}
