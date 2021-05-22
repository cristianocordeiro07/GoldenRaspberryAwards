package com.github.cristianocordeiro07.gra;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class GoldenRaspberryAwardsApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @LocalServerPort
    private int port;

    private final String urlRest = "/worstMovie/producerAwardsInterval";

    TestRestTemplate restTemplate = new TestRestTemplate();

    HttpHeaders headers = new HttpHeaders();

    @Test
    public void testGetProducerAwardsInverval() {

        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                createURL(), HttpMethod.GET, entity, String.class);

        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void testExptectedValues() throws Exception {

        mockMvc.perform(get(urlRest)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.min").exists())
                .andExpect(jsonPath("$.min").isArray())
                .andExpect(jsonPath("$.min.[0].producer").exists())
                .andExpect(jsonPath("$.min.[0].producer").isString())
                .andExpect(jsonPath("$.min.[0].interval").exists())
                .andExpect(jsonPath("$.min.[0].interval").isNumber())
                .andExpect(jsonPath("$.min.[0].previousWin").exists())
                .andExpect(jsonPath("$.min.[0].previousWin").isNumber())
                .andExpect(jsonPath("$.min.[0].followingWin").exists())
                .andExpect(jsonPath("$.min.[0].followingWin").isNumber())
                .andExpect(jsonPath("$.max").exists())
                .andExpect(jsonPath("$.max").isArray())
                .andExpect(jsonPath("$.max.[0].producer").exists())
                .andExpect(jsonPath("$.max.[0].producer").isString())
                .andExpect(jsonPath("$.max.[0].interval").exists())
                .andExpect(jsonPath("$.max.[0].interval").isNumber())
                .andExpect(jsonPath("$.max.[0].previousWin").exists())
                .andExpect(jsonPath("$.max.[0].previousWin").isNumber())
                .andExpect(jsonPath("$.max.[0].followingWin").exists())
                .andExpect(jsonPath("$.max.[0].followingWin").isNumber());
    }

    private String createURL() {
        return "http://localhost:" + port + urlRest;
    }
}
