package com.example.store.model.requests;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateProductRequest {

    @NotNull
    private String name;

    @NotNull
    private Double price;

    @NotNull
    private LocalDateTime creationDate;
}
