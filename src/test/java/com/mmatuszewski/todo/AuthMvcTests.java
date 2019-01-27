package com.mmatuszewski.todo;

import com.mmatuszewski.todo.security.jwt.AuthenticationFilter;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TodoApplication.class)
public class AuthMvcTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private AuthenticationFilter authenticationFilter;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext)
                                        .addFilter(authenticationFilter)
                                        .build();
    }

    @Test
    public void should_signup_user() throws Exception
    {
        mockMvc.perform(post("/auth/signup")
                .contentType(APPLICATION_JSON)
                .content(new JSONObject()
                        .put("username","username")
                        .put("password","password")
                        .put("email","email@gm.pl")
                        .toString()))
                .andExpect(status().is2xxSuccessful());
    }


}
