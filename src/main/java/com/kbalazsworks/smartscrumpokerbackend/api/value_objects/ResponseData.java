package com.kbalazsworks.smartscrumpokerbackend.api.value_objects;

public record ResponseData<T>(
    T data,
    Boolean success,
    int errorCode,
    String requestId,
    String socketResponseDestination
)
{
}
