package com.hedgecourt.sandbox.api;

import com.hedgecourt.spring.lib.HelloWorld;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

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

  @GetMapping("/test")
  public Map<String, Object> getTestMessage() {
    Map<String, Object> response = new HashMap<>();
    response.put("message", HelloWorld.sayHello());
    response.put("rockin-around", "the xmas tree");

    response.put("hc", getHcProperties());

    return response;
  }

  @GetMapping("/nested/abc")
  public Map<String, Object> getNestedMessage() {
    Map<String, Object> response = new HashMap<>();
    response.put("message", HelloWorld.sayHello());
    response.put("nested", Boolean.TRUE);

    response.put("hc", getHcProperties());

    return response;
  }
}
