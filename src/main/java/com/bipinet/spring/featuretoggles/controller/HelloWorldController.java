package com.bipinet.spring.featuretoggles.controller;

import com.bipinet.spring.featuretoggles.togglez.annotation.FeatureToggle;
import com.bipinet.spring.featuretoggles.togglez.toggles.FeatureToggles;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    private static final String GREETING = "Greetings from Spring Boot!";
    private static final String INFO = "INFO works!";

	@RequestMapping("/greeting")
    public ResponseEntity<?> greeting() {
        if (FeatureToggles.HELLO_WORLD.isActive()) {
            StringBuilder sb = new StringBuilder(GREETING);
            if (FeatureToggles.REVERSE_GREETING.isActive()) {
                sb.reverse();
            }
            return ResponseEntity.ok().body(sb.toString());
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping("/info")
    @FeatureToggle(value = FeatureToggles.INFO)
    public ResponseEntity<?> info() {
        return  ResponseEntity.ok().body(INFO);
    }
}