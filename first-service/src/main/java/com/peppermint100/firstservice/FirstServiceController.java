package com.peppermint100.firstservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/first-service")
public class FirstServiceController {

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to first service";
    }

    @GetMapping("/message")
    public String message(@RequestHeader("first-request") String firstRequestHeader) {
        System.out.println(firstRequestHeader);
        return "First Service Message";
    }

    @GetMapping("/check")
    public String check() {
        return "First Service Check";
    }
}
