package com.peppermint100.secondservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/second-service")
public class SecondServiceController {

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to second service";
    }

    @GetMapping("/message")
    public String message(@RequestHeader("second-request") String secondRequestHeader) {
        System.out.println(secondRequestHeader);
        return "Second Service Message";
    }

    @GetMapping("/check")
    public String check() {
        return "Second Service Check";
    }
}
