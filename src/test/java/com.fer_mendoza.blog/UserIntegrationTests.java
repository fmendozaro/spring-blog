package com.fer_mendoza.blog;

import com.fer_mendoza.blog.repositories.UsersRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.*;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BlogApp.class)
@AutoConfigureMockMvc
public class UserIntegrationTests {

    String TOKEN_ATTR_NAME = "org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository.CSRF_TOKEN";
    HttpSessionCsrfTokenRepository httpSessionCsrfTokenRepository = new HttpSessionCsrfTokenRepository();
    CsrfToken csrfToken = httpSessionCsrfTokenRepository.generateToken(new MockHttpServletRequest());

    @Autowired
    private MockMvc mvc;

    @Autowired
    UsersRepository usersRepository;

    // Sanity Test, just to make sure the mvc bean is working
    @Test
    public void contextLoads() throws Exception {
        assertThat(mvc).isNotNull();
    }

    @Test
    public void testShowLoginPage() throws Exception {
        this.mvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Username")));
    }

    @Test
    public void testShowRegisterPage() throws Exception {
        this.mvc.perform(get("/register"))
                .andExpect(status().isOk());
    }

    @Test
    public void testRedirectToLoginIfNoSessionIsActive() throws Exception {
        mvc.perform(get("/users/profile"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("You need to be logged in to be able to see this page")));
    }

    @Test
    public void testRegisterAUser() throws Exception {

        // Create a test user
        this.mvc.perform(post("/users/create")
                .sessionAttr(TOKEN_ATTR_NAME, csrfToken)
                .param(csrfToken.getParameterName(), csrfToken.getToken())
                .param("username", "stacy")
                .param("email", "stacy@email.com")
                .param("password", "malibu")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        // Delete the test user
        usersRepository.delete(usersRepository.findByEmail("stacy@email.com"));

        // Make sure the test user is gone from the DB
        assertThat(usersRepository.findByEmail("stacy@email.com") == null);
    }

}