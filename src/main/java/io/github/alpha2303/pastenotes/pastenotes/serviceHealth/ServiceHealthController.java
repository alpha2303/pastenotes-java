package io.github.alpha2303.pastenotes.pastenotes.serviceHealth;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceHealthController {

    @GetMapping("/health")
    public String health() {
        return "Hello, World!";
    }
}
