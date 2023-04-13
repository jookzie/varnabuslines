package com.varnabuslines.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.varnabuslines.business.BusService;
import com.varnabuslines.controller.bus.CreateBusRequest;
import com.varnabuslines.controller.bus.GetBusResponse;
import com.varnabuslines.controller.bus.UpdateBusRequest;
import com.varnabuslines.domain.Bus;
import com.varnabuslines.domain.Coordinates;
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
import static org.mockito.ArgumentMatchers.anyLong;
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
class BusControllerTests
{
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BusService service;

    private static final String adminId = "1";
    private static final String userId = "2";

    private final Bus mockBus = new Bus()
    {{
        setId(0L);
        setCoordinates(new Coordinates(1.0f, 1.0f));
        setAvailable(true);
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
        var expectedResponse = Mapper.map(mockBus, GetBusResponse.class);
        String expectedJson = objectMapper.writeValueAsString(expectedResponse);
        when(service.get()).thenReturn(List.of(mockBus));

        mockMvc.perform(get("/buses"))
                .andDo(print())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
                .andExpect(content().string(containsString(expectedJson)))
                .andExpect(status().isOk());
        verify(service).get();
    }


    @Test
    void getOne() throws Exception
    {
        var dto = Mapper.map(mockBus, GetBusResponse.class);
        String expectedJson = objectMapper.writeValueAsString(dto);
        when(service.get(mockBus.getId())).thenReturn(Optional.of(mockBus));

        mockMvc.perform(get("/buses/{id}", mockBus.getId()))
                .andDo(print())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
                .andExpect(content().string(containsString(expectedJson)))
                .andExpect(status().isOk());

        verify(service).get(mockBus.getId());
    }

    @Test
    @AsAdmin
    void create_as_admin() throws Exception
    {
        var request = Mapper.map(mockBus, CreateBusRequest.class);

        when(service.create(any(), any())).thenReturn(mockBus);

        mockMvc.perform(post("/buses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated());

        verify(service).create(any(), any());
    }

    @Test
    @AsUser
    void create_as_user() throws Exception
    {
        var request = Mapper.map(mockBus, CreateBusRequest.class);

        mockMvc.perform(post("/buses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }


    @Test
    @AsAdmin
    void update_as_admin() throws Exception
    {
        var request = Mapper.map(mockBus, UpdateBusRequest.class);
        var expectedResponse = Mapper.map(mockBus, GetBusResponse.class);

        String expectedJson = objectMapper.writeValueAsString(expectedResponse);
        when(service.update(anyLong(), any(), any())).thenReturn(Optional.of(mockBus));

        mockMvc.perform(put("/buses/{id}", mockBus.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
                .andExpect(content().string(containsString(expectedJson)))
                .andExpect(status().isOk());

        verify(service).update(anyLong(), any(), any());
    }

    @Test
    @AsUser
    void update_as_user() throws Exception
    {
        var request = Mapper.map(mockBus, UpdateBusRequest.class);

        mockMvc.perform(put("/buses/{id}", mockBus.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @AsAdmin
    void delete_as_admin() throws Exception
    {
        mockMvc.perform(delete("/buses/{id}", mockBus.getId()))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(service).delete(mockBus.getId());
    }

    @Test
    @AsUser
    void delete_as_user() throws Exception
    {
        mockMvc.perform(delete("/buses/{id}", mockBus.getId()))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}
