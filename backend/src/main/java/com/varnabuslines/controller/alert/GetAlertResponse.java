package com.varnabuslines.controller.alert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetAlertResponse
{
    private long id;
    private String title;
    private String content;
    private Date timestamp;
}
