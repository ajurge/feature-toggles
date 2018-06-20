package com.bipinet.spring.featuretoggles.mvc;

import com.bipinet.spring.featuretoggles.AbstractIntegrationTest;
import org.junit.Test;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class FormLoginPageIntegrationTest extends AbstractIntegrationTest {

    private static final String URL_FORM_LOGIN = "/form-login";

    @Test
    public void accessProtectedRedirectsToLogin() throws Exception {
        final MvcResult mvcResult = this.mockMvc.perform(get(URL_FORM_LOGIN))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        assertThat(mvcResult.getResponse().getRedirectedUrl()).endsWith("/login");
    }

    @Test
    public void loginUser() throws Exception {
        this.mockMvc.perform(formLogin().user(USER).password(USER_PASSWORD))
                .andExpect(authenticated());
    }

    @Test
    public void loginInvalidUser() throws Exception {
        this.mockMvc.perform(formLogin().user("invalid").password("invalid"))
                .andExpect(unauthenticated())
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void loginManagerAccessProtected() throws Exception {
        final MvcResult mvcResult = this.mockMvc.perform(formLogin().user(MANAGER).password(MANAGER_PASSWORD))
                .andExpect(authenticated()).andReturn();

        final MockHttpSession httpSession = (MockHttpSession) mvcResult.getRequest().getSession(false);

        this.mockMvc.perform(get(URL_FORM_LOGIN).session(httpSession))
                .andExpect(status().isOk());
    }

    @Test
    public void loginUserAccessProtectedInvalid() throws Exception {
        final MvcResult mvcResult = this.mockMvc.perform(formLogin().user(USER).password(USER_PASSWORD))
                .andExpect(authenticated()).andReturn();

        final MockHttpSession httpSession = (MockHttpSession) mvcResult.getRequest().getSession(false);

        this.mockMvc.perform(get(URL_FORM_LOGIN).session(httpSession))
                .andExpect(authenticated()).andExpect(status().isForbidden());
//        this.mockMvc.perform(get(URL_FORM_LOGIN).session(httpSession))
//                .andExpect(redirectedUrl("/access-denied"));
    }

    @Test
    public void loginAdminValidateLogout() throws Exception {
        final MvcResult mvcResult = this.mockMvc.perform(formLogin().user(ADMIN).password(ADMIN_PASSWORD))
                .andExpect(authenticated()).andReturn();

        final MockHttpSession httpSession = (MockHttpSession) mvcResult.getRequest().getSession(false);

        this.mockMvc.perform(post("/logout").with(csrf()).session(httpSession))
                .andExpect(unauthenticated())
                .andExpect(redirectedUrl("/login?logout"));
        this.mockMvc.perform(get(URL_FORM_LOGIN).session(httpSession))
                .andExpect(unauthenticated())
                .andExpect(status().is3xxRedirection());
    }
}