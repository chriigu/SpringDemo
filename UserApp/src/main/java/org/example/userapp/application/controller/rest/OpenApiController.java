package org.example.userapp.application.controller.rest;

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
