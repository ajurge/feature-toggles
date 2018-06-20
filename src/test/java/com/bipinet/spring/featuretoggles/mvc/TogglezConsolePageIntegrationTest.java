package com.bipinet.spring.featuretoggles.mvc;

import com.bipinet.spring.featuretoggles.AbstractIntegrationTest;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.http.HttpHeaders.WWW_AUTHENTICATE;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TogglezConsolePageIntegrationTest extends AbstractIntegrationTest {

    private static final String URL_TOGGLEZ_CONSOLE = "/togglz-console";


    @Test
    public void accessProtected() throws Exception {
        this.mockMvc.perform(get(URL_TOGGLEZ_CONSOLE))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    public void getWithUserReturnsUnauthenticatedAndPromptsNewLogin() throws Exception {
        testGetReturnsUnauthenticatedAndPromptsNewLogin(USER, USER_PASSWORD);
    }

    @Test
    public void getWithInvalidUserReturnsUnauthenticatedAndPromptsNewLogin() throws Exception {
        testGetReturnsUnauthenticatedAndPromptsNewLogin("invalid", "invalid");
    }

    @Test
    public void getWithEmptyCredentialsReturnsUnauthenticatedAndPromptsNewLogin() throws Exception {
        testGetReturnsUnauthenticatedAndPromptsNewLogin("", "");
    }

    @Test
    public void getWithAdminAccessible() {
        final ResponseEntity<String> response = this.testRestTemplate
                .withBasicAuth(ADMIN, ADMIN_PASSWORD)
                .getForEntity(URL_TOGGLEZ_CONSOLE, String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    private void testGetReturnsUnauthenticatedAndPromptsNewLogin(String user, String password) throws Exception {
        this.mockMvc.perform(get(URL_TOGGLEZ_CONSOLE).with(httpBasic(user, password)))
                .andExpect(unauthenticated())
                .andExpect(header().string(WWW_AUTHENTICATE, "Basic realm=FT_BA_REALM"))
                .andExpect(status().isUnauthorized());
    }
}