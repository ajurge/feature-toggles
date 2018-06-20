package com.bipinet.spring.featuretoggles;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@RunWith(SpringJUnit4ClassRunner.class)
public abstract class AbstractIntegrationTest {

    protected static final String USER = "user";
    protected static final String USER_PASSWORD = "user";
    protected static final String ADMIN = "admin";
    protected static final String ADMIN_PASSWORD = "admin";
    protected static final String MANAGER = "manager";
    protected static final String MANAGER_PASSWORD = "manager";

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected TestRestTemplate testRestTemplate;
}