package com.varnabuslines.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.varnabuslines.business.TicketService;
import com.varnabuslines.controller.tickets.CreateTicketRequest;
import com.varnabuslines.controller.tickets.GetTicketResponse;
import com.varnabuslines.controller.tickets.UpdateTicketRequest;
import com.varnabuslines.domain.Ticket;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ControllerTest
class TicketControllerTests
{
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TicketService service;

    private static final String adminId = "1";
    private static final String userId = "2";

    private final Ticket mockTicket = new Ticket()
    {{
        setId(1L);
        setPrice(1.0f);
        setDuration(1);
    }};


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


    @Test
    void getAll() throws Exception
    {
        var expectedResponse = Mapper.map(mockTicket, GetTicketResponse.class);
        String expectedJson = objectMapper.writeValueAsString(expectedResponse);
        when(service.get()).thenReturn(List.of(mockTicket));

        mockMvc.perform(get("/tickets"))
                .andDo(print())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
                .andExpect(content().string(containsString(expectedJson)))
                .andExpect(status().isOk());

        verify(service).get();
    }


    @Test
    void getOne() throws Exception
    {
        var dto = Mapper.map(mockTicket, GetTicketResponse.class);
        String expectedJson = objectMapper.writeValueAsString(dto);
        when(service.get(mockTicket.getId())).thenReturn(Optional.of(mockTicket));

        mockMvc.perform(get("/tickets/{id}", mockTicket.getId()))
                .andDo(print())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
                .andExpect(content().string(containsString(expectedJson)))
                .andExpect(status().isOk());

        verify(service).get(mockTicket.getId());
    }

    @Test
    @AsAdmin
    void create_as_admin() throws Exception
    {
        var request = Mapper.map(mockTicket, CreateTicketRequest.class);
        var expectedResponse = Mapper.map(mockTicket, GetTicketResponse.class);

        String expectedJson = objectMapper.writeValueAsString(expectedResponse);
        when(service.create(request.getPrice(), request.getDuration())).thenReturn(mockTicket);

        mockMvc.perform(post("/tickets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
                .andExpect(content().string(containsString(expectedJson)))
                .andExpect(status().isCreated());

        verify(service).create(request.getPrice(), request.getDuration());
    }

    @Test
    @AsUser
    void create_as_user() throws Exception
    {
        var request = Mapper.map(mockTicket, CreateTicketRequest.class);

        mockMvc.perform(post("/tickets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }


    @Test
    @AsAdmin
    void update_as_admin() throws Exception
    {
        var request = Mapper.map(mockTicket, UpdateTicketRequest.class);
        var expectedResponse = Mapper.map(mockTicket, GetTicketResponse.class);

        String expectedJson = objectMapper.writeValueAsString(expectedResponse);
        when(service.update(mockTicket.getId(), request.getPrice(), request.getDuration())).thenReturn(Optional.of(mockTicket));

        mockMvc.perform(put("/tickets/{id}", mockTicket.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
                .andExpect(content().string(containsString(expectedJson)))
                .andExpect(status().isOk());

        verify(service).update(mockTicket.getId(), request.getPrice(), request.getDuration());
    }

    @Test
    @AsUser
    void update_as_user() throws Exception
    {
        var request = Mapper.map(mockTicket, UpdateTicketRequest.class);

        mockMvc.perform(put("/tickets/{id}", mockTicket.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @AsAdmin
    void delete_as_admin() throws Exception
    {
        mockMvc.perform(delete("/tickets/{id}", mockTicket.getId()))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(service).delete(mockTicket.getId());
    }

    @Test
    @AsUser
    void delete_as_user() throws Exception
    {
        mockMvc.perform(delete("/tickets/{id}", mockTicket.getId()))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}
