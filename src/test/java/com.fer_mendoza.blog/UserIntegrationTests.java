package com.fer_mendoza.blog;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BlogApp.class)
@AutoConfigureMockMvc
public class UserIntegrationTests {

    @Autowired
    private MockMvc mvc;

    // Sanity Test, just to make sure the mvc bean is working
    @Test
    public void contextLoads() throws Exception {
        assertThat(mvc).isNotNull();
    }

    @Test
    public void testGetHomePage() throws Exception {
        this.mvc.perform(get("/"))
                .andExpect(status().isOk());
    }

    @Test
    public void testRegisterUser() throws Exception {
        this.mvc.perform(post("/register")
                .param("username", "stacy")
                .param("email", "stacy@email.com")
                .param("password", "kitty")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/"));
    }

//    @Test
//    public void testRegisterUserMismatchedPasswords() throws Exception{
//        this.mvc.perform(post("/register")
//                .param("username", "stacy")
//                .param("email", "stacy@email.com")
//                .param("password", "kitty")
//                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
//                .andExpect(status().isFound())
//                .andExpect(redirectedUrl("/register?error"));
//    }

}