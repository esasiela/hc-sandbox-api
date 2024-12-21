package com.hedgecourt.sandbox.api;

import com.hedgecourt.spring.HelloWorld;
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
  @GetMapping("/test")
  public String getTestMessage() {
    return String.format("{\n\t\"message\": \"%s\"\n}\n", HelloWorld.sayHello());
  }
}
