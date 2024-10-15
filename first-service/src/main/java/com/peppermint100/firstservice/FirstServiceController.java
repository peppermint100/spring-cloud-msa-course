package com.peppermint100.firstservice;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/first-service")
@Slf4j
public class FirstServiceController {

    Environment env;

    @Autowired
    public FirstServiceController(Environment env) {
        this.env = env;
    }

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
    public String check(HttpServletRequest request) {
        log.info("Server port = {}", request.getServerPort());
        return String.format("First Service Check %s", env.getProperty("local.server.port"));
    }
}
