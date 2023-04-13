package com.varnabuslines.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.varnabuslines.business.UserService;
import com.varnabuslines.controller.user.CreateUserRequest;
import com.varnabuslines.controller.user.CreateUserResponse;
import com.varnabuslines.controller.user.GetUserResponse;
import com.varnabuslines.controller.user.UpdateUserRequest;
import com.varnabuslines.domain.Role;
import com.varnabuslines.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ControllerTest
class AdminControllerTests
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


    @MockBean
    private UserService service;

    @Test
    @WithMockUser(username = adminId, roles = "ADMIN")
    void createAdmin_asAdmin() throws Exception
    {
        var request = Mapper.map(mockAdmin, CreateUserRequest.class);
        var expectedResponse = Mapper.map(mockAdmin, CreateUserResponse.class);
        var expectedJson = objectMapper.writeValueAsString(expectedResponse);

        when(service.createAdmin(request.getEmail(), request.getPassword())).thenReturn(mockAdmin);
        mockMvc.perform(post("/admins")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(content().contentType(APPLICATION_JSON_VALUE))
                .andExpect(content().string(containsString(expectedJson)))
                .andExpect(status().isCreated());

        verify(service).createAdmin(request.getEmail(), request.getPassword());
    }

    @Test
    @WithMockUser(username = adminId, roles = "USER")
    void createAdmin_asUser() throws Exception
    {
        var request = Mapper.map(mockAdmin, CreateUserRequest.class);

        when(service.createAdmin(request.getEmail(), request.getPassword())).thenReturn(mockAdmin);
        mockMvc.perform(post("/admins")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(content().string(""))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = adminId, roles = "ADMIN")
    void update_as_admin() throws Exception
    {
        var requestBody = new UpdateUserRequest(
                mockAdmin.getEmail(),
                mockAdmin.getHashedPassword(),
                mockAdmin.getRole().toString());

        when(service.updateAsUser(
                mockAdmin.getId(),
                mockAdmin.getEmail(),
                mockAdmin.getHashedPassword()))
                    .thenReturn(Optional.of(mockAdmin));

        var expectedResponse = Mapper.map(mockAdmin, GetUserResponse.class);
        var expectedJson = objectMapper.writeValueAsString(expectedResponse);

        mockMvc.perform(put("/users/{id}", mockAdmin.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(expectedJson)))
                .andReturn().getResponse().getContentAsString();

        verify(service).updateAsUser(
                mockAdmin.getId(),
                mockAdmin.getEmail(),
                mockAdmin.getHashedPassword());
    }
}
