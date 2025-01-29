package com.hedgecourt.sandbox.api;

import com.hedgecourt.spring.lib.HelloWorld;
import com.hedgecourt.spring.lib.annotation.HcPublicEndpoint;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SandboxController {
  private static final Logger log = LoggerFactory.getLogger(SandboxController.class);

  @Value("${hc.test}")
  private String hcTestProperty;

  @Value("${hc.common}")
  private String hcCommonProperty;

  @Value("${hc.env}")
  private String hcEnv;

  private Map<String, Object> getHcProperties() {
    Map<String, Object> hcResp = new HashMap<>();
    hcResp.put("test", hcTestProperty);
    hcResp.put("common", hcCommonProperty);
    hcResp.put("env", hcEnv);
    return hcResp;
  }

  private void addAuthResponse(Authentication auth, Map<String, Object> response) {
    if (auth != null) {
      Map<String, Object> authResponse = new HashMap<>();
      authResponse.put("username", auth.getName());
      response.put("auth", authResponse);
    }
  }

  @GetMapping("/test")
  @HcPublicEndpoint
  public Map<String, Object> getTestMessage(Authentication auth) {
    Map<String, Object> response = new HashMap<>();
    response.put("message", HelloWorld.sayHello());
    response.put("rockin-around", "the xmas tree");

    response.put("hc", getHcProperties());

    addAuthResponse(auth, response);
    return response;
  }

  @GetMapping("/nested/test")
  @HcPublicEndpoint
  public Map<String, Object> getNestedMessage(Authentication auth) {
    Map<String, Object> response = new HashMap<>();
    response.put("message", HelloWorld.sayHello());
    response.put("nested", Boolean.TRUE);

    response.put("hc", getHcProperties());

    addAuthResponse(auth, response);
    return response;
  }

  @GetMapping("/secure")
  @Secured("SCOPE_sandbox:read")
  public ResponseEntity<Map<String, Object>> getSecure(Authentication auth) {

    if (log.isInfoEnabled() && auth == null) log.info("/secure - null auth");
    if (log.isInfoEnabled() && auth != null)
      log.info("/secure, username({}) authorities({})", auth.getName(), auth.getAuthorities());

    Map<String, Object> response = new HashMap<>();
    response.put("apple", "beans");

    addAuthResponse(auth, response);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }
}
