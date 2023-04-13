package com.varnabuslines.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.varnabuslines.business.UserService;
import com.varnabuslines.controller.user.CreateUserRequest;
import com.varnabuslines.controller.user.CreateUserResponse;
import com.varnabuslines.controller.user.GetUserResponse;
import com.varnabuslines.domain.Role;
import com.varnabuslines.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ControllerTest
class UserControllerTests
{
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;

    private static final String adminId = "1";
    private static final String userId = "2";

    private final User mockAdmin = new User(
            Long.parseLong(adminId),
            "admin@test.com",
            "password",
            Role.ADMIN);

    private final User mockUser = new User(
            Long.parseLong(userId),
            "user@test.com",
            "password",
            Role.USER);

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @WithMockUser(username = adminId, roles = "ADMIN")
    private @interface AsAdmin
    {
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @WithMockUser(username = userId, roles = {"USER"})
    private @interface AsUser
    {
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @WithMockUser(username = "", roles = "")
    private @interface AsGuest
    {
    }

    @MockBean
    private UserService service;

    @Test
    @AsAdmin
    void getAllUsers_as_admin() throws Exception
    {
        var dto = Mapper.map(mockUser, GetUserResponse.class);
        String expectedJson = objectMapper.writeValueAsString(dto);

        when(service.get()).thenReturn(List.of(mockUser));

        mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
                .andExpect(content().string(containsString(expectedJson)))
                .andExpect(status().isOk());

        verify(service).get();
    }

    @Test
    @AsUser
    void getAllUsers_as_user() throws Exception
    {
        mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }


    @Test
    @AsAdmin
    void getUser_as_admin() throws Exception
    {
        when(service.get(mockUser.getId())).thenReturn(Optional.of(mockUser));
        var expectedResponse = Mapper.map(mockUser, GetUserResponse.class);
        String expectedJson = objectMapper.writeValueAsString(expectedResponse);

        mockMvc.perform(get("/users/{id}", mockUser.getId())
                            .contentType(MediaType.APPLICATION_JSON))
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
                .andExpect(content().string(containsString(expectedJson)))
                .andExpect(status().isOk());

        verify(service).get(mockUser.getId());
    }


    @Test
    @AsAdmin
    void deleteUser_as_admin() throws Exception
    {
        mockMvc.perform(delete("/users/{id}", mockUser.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        verify(service).delete(mockUser.getId());
    }



    @Test
    @AsGuest
    void createUser() throws Exception
    {
        var requestBody = Mapper.map(mockUser, CreateUserRequest.class);
        when(service.createUser(requestBody.getEmail(), requestBody.getPassword())).thenReturn(mockUser);

        var expectedResponse = Mapper.map(mockUser, CreateUserResponse.class);
        String expectedJson = objectMapper.writeValueAsString(expectedResponse);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
                .andExpect(content().string(containsString(expectedJson)))
                .andExpect(status().isCreated());

        verify(service).createUser(requestBody.getEmail(), requestBody.getPassword());
    }

    @Test
    @AsAdmin
    void deleteLastAdmin() throws Exception
    {
        doThrow(new IllegalStateException()).when(service).delete(mockAdmin.getId());
        mockMvc.perform(delete("/users/{id}", mockAdmin.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
        verify(service).delete(mockAdmin.getId());
    }

}
