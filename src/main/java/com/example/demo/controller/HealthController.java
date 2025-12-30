package com.example.demo.controller;



import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
	 @GetMapping("/api/healthz")
	    public Map<String, Boolean> health() {
	        return Map.of("ok", true);
	    }
}
