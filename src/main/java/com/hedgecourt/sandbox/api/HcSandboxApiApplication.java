package com.hedgecourt.sandbox.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.hedgecourt.spring.HelloWorld;
import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class HcSandboxApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(HcSandboxApiApplication.class, args);
  }
}

@RestController
class TestController {

  private final ObjectMapper objectMapper;

  public TestController(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
  }

  @GetMapping("/test")
  public String getTestMessage() {
    Map<String, Object> response = new HashMap<>();
    response.put("message", HelloWorld.sayHello());
    response.put("silent", "night");

    try {
      return objectMapper.writeValueAsString(response) + "\n";
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Failed to convert response to JSON", e);
    }
  }
}
