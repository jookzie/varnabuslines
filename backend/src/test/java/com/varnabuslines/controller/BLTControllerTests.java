package com.varnabuslines.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.varnabuslines.business.BusLineService;
import com.varnabuslines.controller.tickets.GetTicketResponse;
import com.varnabuslines.domain.BusLine;
import com.varnabuslines.domain.Ticket;
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
class BLTControllerTests
{
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BusLineService service;

    private final Ticket mockTicket = new Ticket()
    {{
        setId(2L);
    }};

    private final BusLine mockBusLine = new BusLine()
    {{
        setId("test");
        setTickets(Set.of(mockTicket));
    }};
    private final ObjectMapper objectMapper = new ObjectMapper();

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
    void getTicketIds_ByBuslineId() throws Exception
    {
        var expectedResponse = Mapper.map(mockTicket, GetTicketResponse.class);
        String expectedJson = objectMapper.writeValueAsString(expectedResponse);

        when(service.get(mockBusLine.getId())).thenReturn(Optional.of(mockBusLine));

        mockMvc.perform(get("/buslines/{busLineId}/tickets", mockBusLine.getId()))
                .andDo(print())
                .andExpect(header().string("Content-Type", APPLICATION_JSON_VALUE))
                .andExpect(content().string(containsString(expectedJson)))
                .andExpect(status().isOk());

        verify(service).get(mockBusLine.getId());
    }
}