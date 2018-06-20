package com.bipinet.spring.featuretoggles.mvc;

import com.bipinet.spring.featuretoggles.AbstractIntegrationTest;
import org.junit.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class IndexPageIntegrationTest extends AbstractIntegrationTest {

    @Test
    public void accessUnprotected() throws Exception {
        this.mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }
}