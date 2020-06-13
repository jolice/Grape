package com.github.jolice.exception;

import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = false)
public class GraphCycleException extends RuntimeException {

    private final Object from;
    private final Object to;


}
