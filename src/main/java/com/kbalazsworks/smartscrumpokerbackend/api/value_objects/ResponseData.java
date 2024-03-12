package com.kbalazsworks.smartscrumpokerbackend.api.value_objects;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ResponseData<T>
{
    private final T       data;
    private final Boolean success;
    private final int     errorCode;
    private final String  requestId;
}
