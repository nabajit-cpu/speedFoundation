package com.example.api_gateway.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/api-gateway")
public class APIGatewayController {
    @GetMapping("/test")
    public String test(){
        return "API Gateway 8080 is working";
    }
}
