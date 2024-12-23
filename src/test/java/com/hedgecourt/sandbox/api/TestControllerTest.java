package com.hedgecourt.sandbox.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class TestControllerTest {

  @Autowired private MockMvc mockMvc;

  @Test
  void testGetNestedMessage() throws Exception {
    mockMvc
        .perform(get("/nested/abc"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.message").value("Hello, World!"))
        .andExpect(jsonPath("$.nested").value(true))
        .andExpect(jsonPath("$.hc.test").value("testTest"))
        .andExpect(jsonPath("$.hc.common").value("value"))
        .andExpect(jsonPath("$.hc.env").value("testEnv"));
  }
}
