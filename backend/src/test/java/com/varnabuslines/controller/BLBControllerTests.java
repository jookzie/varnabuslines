package com.varnabuslines.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.varnabuslines.business.BusLineService;
import com.varnabuslines.domain.Bus;
import com.varnabuslines.domain.BusLine;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ControllerTest
class BLBControllerTests
{
    private static final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BusLineService service;

    private final Bus mockBus = new Bus()
    {{
        setId(2L);
    }};

    private final BusLine mockBusLine = new BusLine()
    {{
        setId("test");
        setBuses(Set.of(mockBus));
    }};

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @WithMockUser(username = "2", roles = {"ADMIN", "USER"})
    private @interface AsAdmin
    {
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @WithMockUser(username = "1", roles = {"USER"})
    private @interface AsUser
    {
    }

    @Test
    void getBusIds_ByBuslineId() throws Exception
    {
        when(service.get(mockBusLine.getId())).thenReturn(Optional.of(mockBusLine));

        String expectedJson = objectMapper.writeValueAsString(mockBusLine.getBuses());

        mockMvc.perform(get("/buslines/{busLineId}/buses", mockBusLine.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
                .andExpect(content().string(containsString(expectedJson)));

        verify(service).get(mockBusLine.getId());
    }

}