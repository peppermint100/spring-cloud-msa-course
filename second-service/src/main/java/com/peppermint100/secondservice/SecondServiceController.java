package com.peppermint100.secondservice;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/second-service")
@Slf4j
public class SecondServiceController {

    Environment env;

    @Autowired
    public SecondServiceController(Environment env) {
        this.env = env;
    }

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
    public String check(HttpServletRequest request) {
        log.info("Server Port: {}", request.getServerPort());
        return String.format("Check Second Service On Port: %s", env.getProperty("local.server.port"));
    }
}
