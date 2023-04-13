package com.varnabuslines.controller.login;

import com.varnabuslines.business.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/login")
@AllArgsConstructor
public class LoginController
{
    private final AuthenticationService auth;

    @PostMapping
    public ResponseEntity<LoginResponse> login(@RequestBody final LoginRequest request)
    {
        Optional<String> tokenOpt = auth.authenticate(request.getEmail(), request.getPassword());

        if (tokenOpt.isEmpty())
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        else
            return ResponseEntity.ok(new LoginResponse(tokenOpt.get()));
    }
}
