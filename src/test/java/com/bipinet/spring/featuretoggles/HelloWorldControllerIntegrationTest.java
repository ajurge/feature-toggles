package com.bipinet.spring.featuretoggles;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bipinet.spring.featuretoggles.togglez.toggles.FeatureToggles;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.togglz.junit.TogglzRule;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class HelloWorldControllerIntegrationTest {

    private static final String GREETING_URL = "/greeting";
    private static final String INFO_URL = "/info";

    @Rule
    public TogglzRule togglzRule = TogglzRule.allDisabled(FeatureToggles.class);

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void greetingReturnsHttp404WhenHelloWorldFeatureDisabled() throws Exception {
        mockMvc.perform(get(GREETING_URL)).andExpect(status().isNotFound());
    }

    @Test
    public void infoReturnsHttp404WhenInfoFeatureDisabled() throws Exception {
        mockMvc.perform(get(INFO_URL)).andExpect(status().isNotFound());
    }

    @Test
    public void greetingReturnsHttp200WhenHelloWorldFeatureEnabled() throws Exception {
        togglzRule.enable(FeatureToggles.HELLO_WORLD);
        mockMvc.perform(get(GREETING_URL)).andExpect(status().isOk())
                .andExpect(content().string("Greetings from Spring Boot!"));
    }

    @Test
    public void greetingReturnsHttp200WhenHelloWorldFeatureAndReverseGreetingEnabled() throws Exception {
        togglzRule.enable(FeatureToggles.HELLO_WORLD);
        togglzRule.enable(FeatureToggles.REVERSE_GREETING);
        mockMvc.perform(get(GREETING_URL)).andExpect(status().isOk())
                .andExpect(content().string("!tooB gnirpS morf sgniteerG"));
    }

    @Test
    public void infoReturnsHttp200WhenInfoFeatureEnabled() throws Exception {
        togglzRule.enable(FeatureToggles.INFO);
        mockMvc.perform(get(INFO_URL)).andExpect(status().isOk())
                .andExpect(content().string("INFO works!"));
    }
}