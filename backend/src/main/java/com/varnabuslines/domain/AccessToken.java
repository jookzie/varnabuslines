package com.varnabuslines.domain;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AccessToken
{
    private long userId;
    private List<String> roles;
}
