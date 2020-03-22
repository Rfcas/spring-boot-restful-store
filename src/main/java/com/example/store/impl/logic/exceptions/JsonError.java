package com.example.store.impl.logic.exceptions;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JsonError {
    private int code;
    private String status;
    private StackTraceElement[] stackTrace;
}

