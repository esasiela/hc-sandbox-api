package com.hedgecourt.sandbox.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.hedgecourt")
public class HcSandboxApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(HcSandboxApiApplication.class, args);
  }
}
