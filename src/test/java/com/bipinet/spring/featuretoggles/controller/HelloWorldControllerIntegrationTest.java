package com.bipinet.spring.featuretoggles.controller;

import com.bipinet.spring.featuretoggles.AbstractIntegrationTest;
import com.bipinet.spring.featuretoggles.togglez.toggles.FeatureToggles;
import org.junit.Test;
import org.togglz.core.context.FeatureContext;
import org.togglz.core.repository.FeatureState;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class HelloWorldControllerIntegrationTest extends AbstractIntegrationTest {

    private static final String GREETING_URL = "/greeting";
    private static final String INFO_URL = "/info";


    @Test
    public void greetingReturnsHttp404WhenHelloWorldFeatureDisabled() throws Exception {
        this.mockMvc.perform(get(GREETING_URL)).andExpect(status().isNotFound());
    }

    @Test
    public void infoReturnsHttp404WhenInfoFeatureDisabled() throws Exception {
        this.mockMvc.perform(get(INFO_URL)).andExpect(status().isNotFound());
    }

    @Test
    public void greetingReturnsHttp200WhenHelloWorldFeatureEnabled() throws Exception {
        FeatureContext.getFeatureManager().setFeatureState(new FeatureState(FeatureToggles.HELLO_WORLD, true));
        this.mockMvc.perform(get(GREETING_URL)).andExpect(status().isOk())
                .andExpect(content().string("Greetings from Spring Boot!"));
    }

    @Test
    public void greetingReturnsHttp200WhenHelloWorldFeatureAndReverseGreetingEnabled() throws Exception {
        FeatureContext.getFeatureManager().setFeatureState(new FeatureState(FeatureToggles.HELLO_WORLD, true));
        FeatureContext.getFeatureManager().setFeatureState(new FeatureState(FeatureToggles.REVERSE_GREETING, true));
        this.mockMvc.perform(get(GREETING_URL)).andExpect(status().isOk())
                .andExpect(content().string("!tooB gnirpS morf sgniteerG"));
    }

    @Test
    public void infoReturnsHttp200WhenInfoFeatureEnabled() throws Exception {
        FeatureContext.getFeatureManager().setFeatureState(new FeatureState(FeatureToggles.INFO, true));
        this.mockMvc.perform(get(INFO_URL)).andExpect(status().isOk())
                .andExpect(content().string("INFO works!"));
    }
}