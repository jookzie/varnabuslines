package com.varnabuslines.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.varnabuslines.business.BusLineService;
import com.varnabuslines.controller.busline.CreateBusLineRequest;
import com.varnabuslines.controller.busline.GetBusLineResponse;
import com.varnabuslines.controller.busline.UpdateBusLineRequest;
import com.varnabuslines.domain.BusLine;
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
import static org.mockito.ArgumentMatchers.any;
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
class BusLineControllerTests
{
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BusLineService service;

    private static final String adminId = "1";
    private static final String userId = "2";

    private final BusLine mockBusLine = new BusLine("1");


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
        var expectedResponse = Mapper.map(mockBusLine, GetBusLineResponse.class);
        String expectedJson = objectMapper.writeValueAsString(expectedResponse);
        when(service.get()).thenReturn(List.of(mockBusLine));

        mockMvc.perform(get("/buslines"))
                .andDo(print())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
                .andExpect(content().string(containsString(expectedJson)))
                .andExpect(status().isOk());

        verify(service).get();
    }


    @Test
    void getOne() throws Exception
    {
        var dto = Mapper.map(mockBusLine, GetBusLineResponse.class);
        String expectedJson = objectMapper.writeValueAsString(dto);
        when(service.get(mockBusLine.getId())).thenReturn(Optional.of(mockBusLine));

        mockMvc.perform(get("/buslines/{id}", mockBusLine.getId()))
                .andDo(print())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
                .andExpect(content().string(containsString(expectedJson)))
                .andExpect(status().isOk());

        verify(service).get(mockBusLine.getId());
    }

    @Test
    @AsAdmin
    void create_as_admin() throws Exception
    {
        var request = Mapper.map(mockBusLine, CreateBusLineRequest.class);
        var expectedResponse = Mapper.map(mockBusLine, GetBusLineResponse.class);

        String expectedJson = objectMapper.writeValueAsString(expectedResponse);
        when(service.create(any())).thenReturn(mockBusLine);

        mockMvc.perform(post("/buslines")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
                .andExpect(content().string(containsString(expectedJson)))
                .andExpect(status().isCreated());

        verify(service).create(any());
    }

    @Test
    @AsUser
    void create_as_user() throws Exception
    {
        var request = Mapper.map(mockBusLine, CreateBusLineRequest.class);

        mockMvc.perform(post("/buslines")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }


    @Test
    @AsAdmin
    void update_as_admin() throws Exception
    {
        var request = Mapper.map(mockBusLine, UpdateBusLineRequest.class);
        var expectedResponse = Mapper.map(mockBusLine, GetBusLineResponse.class);

        String expectedJson = objectMapper.writeValueAsString(expectedResponse);
        when(service.update(mockBusLine.getId(), request.getId(), request.isAvailable())).thenReturn(Optional.of(mockBusLine));

        mockMvc.perform(put("/buslines/{id}", mockBusLine.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
                .andExpect(content().string(containsString(expectedJson)))
                .andExpect(status().isOk());

        verify(service).update(mockBusLine.getId(), request.getId(), request.isAvailable());
    }

    @Test
    @AsUser
    void update_as_user() throws Exception
    {
        var request = Mapper.map(mockBusLine, UpdateBusLineRequest.class);

        mockMvc.perform(put("/buslines/{id}", mockBusLine.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @AsAdmin
    void delete_as_admin() throws Exception
    {
        mockMvc.perform(delete("/buslines/{id}", mockBusLine.getId()))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(service).delete(mockBusLine.getId());
    }

    @Test
    @AsUser
    void delete_as_user() throws Exception
    {
        mockMvc.perform(delete("/buslines/{id}", mockBusLine.getId()))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}
