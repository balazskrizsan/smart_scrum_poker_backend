package com.kbalazsworks.smartscrumpokerbackend.controllers.index;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController("AccountGetByReviewIdAction")
@RequestMapping("/")
public class GetIndexController
{
    @GetMapping("/")
    public String action()
    {
        return "hello";
    }
}
