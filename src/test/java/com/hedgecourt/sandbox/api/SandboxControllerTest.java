package com.hedgecourt.sandbox.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.hedgecourt.spring.test.HcSpringBaseControllerTest;
import java.util.Set;
import java.util.stream.Stream;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class SandboxControllerTest extends HcSpringBaseControllerTest {
  private static final Logger log = LoggerFactory.getLogger(SandboxControllerTest.class);

  @Autowired private MockMvc mockMvc;

  @Override
  public Stream<Arguments> getEndpointUseCases() {
    return Stream.of(
        Arguments.of(
            Named.of(
                "Secured Endpoint",
                new EndpointUseCase("sandbox:read", HttpMethod.GET, "/secure", null))));
  }

  @Test
  void testGetNestedMessage() throws Exception {
    mockMvc
        .perform(get("/nested/test"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.message").value("Hello, World!"))
        .andExpect(jsonPath("$.nested").value(true))
        .andExpect(jsonPath("$.hc.test").value("bar"))
        .andExpect(jsonPath("$.hc.common").value("value"))
        .andExpect(jsonPath("$.hc.env").value("test"));
  }

  @Test
  public void testGetSecure() throws Exception {

    mockMvc
        .perform(
            get("/secure")
                .header("Authorization", "Bearer " + generateJwt(Set.of("sandbox:read")))
                .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk());
  }
}
