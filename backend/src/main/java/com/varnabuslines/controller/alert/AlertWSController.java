package com.varnabuslines.controller.alert;

import com.varnabuslines.business.AlertService;
import com.varnabuslines.controller.Mapper;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;



@Controller
@AllArgsConstructor
public class AlertWSController
{
    private final AlertService service;

    @MessageMapping(value = "/alerts")
    @SendTo("/topic/alerts")
    public GetAlertResponse send(final GetAlertRequest request) {

        var opt = service.get(request.getId());
        if(opt.isEmpty())
            return null;
        return Mapper.map(opt.get(), GetAlertResponse.class);
    }
}
