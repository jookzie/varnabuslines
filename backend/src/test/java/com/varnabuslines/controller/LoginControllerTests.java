package com.varnabuslines.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.varnabuslines.business.AuthenticationService;
import com.varnabuslines.controller.login.LoginRequest;
import com.varnabuslines.controller.login.LoginResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ControllerTest
class LoginControllerTests
{
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationService service;

    @Test
    void login_pass() throws Exception
    {
        var request = new LoginRequest("user", "pass");
        var token = "token";
        var expectedJson = objectMapper.writeValueAsString(new LoginResponse(token));
        when(service.authenticate(request.getEmail(), request.getPassword())).thenReturn(Optional.of(token));

        mockMvc.perform(post("/login")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
                .andExpect(content().string(containsString(expectedJson)))
                .andExpect(status().isOk());

        verify(service).authenticate(request.getEmail(), request.getPassword());
    }

    @Test
    void login_fail() throws Exception
    {
        var request = new LoginRequest("user", "pass");
        when(service.authenticate(request.getEmail(), request.getPassword())).thenReturn(Optional.empty());

        mockMvc.perform(post("/login")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andExpect(content().string(""));
    }
}
