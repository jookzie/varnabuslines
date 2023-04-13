package com.varnabuslines.controller.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetUserResponse
{
    private long id;
    private String email;
    private String role;
    private long favoriteRouteId;
}