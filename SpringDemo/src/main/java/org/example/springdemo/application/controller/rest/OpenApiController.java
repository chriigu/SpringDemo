package org.example.springdemo.application.controller.rest;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

//@RestController
//@RequestMapping("/v3/api-docs")
//public class OpenApiController {
//
//    @GetMapping
//    public ResponseEntity<String> getOpenApiSpec() {
//        try {
//            // Load the file from classpath (inside the external JAR)
//            Resource resource = new ClassPathResource("META-INF/rest-application.json");
//
//            // Read file content
//            InputStream inputStream = resource.getInputStream();
//            String content = new Scanner(inputStream, StandardCharsets.UTF_8).useDelimiter("\\A").next();
//
//            return ResponseEntity.ok(content);
//        } catch (Exception e) {
//            return ResponseEntity.status(500).body("Error loading OpenAPI spec: " + e.getMessage());
//        }
//    }
//}
