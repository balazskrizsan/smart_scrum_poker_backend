package com.kbalazsworks.smartscrumpokerbackend.api.controllers.index;

import com.kbalazsworks.smartscrumpokerbackend.api.builders.ResponseEntityBuilder;
import com.kbalazsworks.smartscrumpokerbackend.api.exceptions.ApiException;
import com.kbalazsworks.smartscrumpokerbackend.api.value_objects.ResponseData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController("AccountGetByReviewIdAction")
@RequestMapping("/")
public class GetIndexController
{
    @GetMapping("/")
    public ResponseEntity<ResponseData<String>> action() throws ApiException
    {
        return new ResponseEntityBuilder<String>().data("hello").build();
    }
}
