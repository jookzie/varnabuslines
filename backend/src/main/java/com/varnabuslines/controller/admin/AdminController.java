package com.varnabuslines.controller.admin;

import com.varnabuslines.business.UserService;
import com.varnabuslines.controller.Mapper;
import com.varnabuslines.controller.user.CreateUserRequest;
import com.varnabuslines.controller.user.CreateUserResponse;
import com.varnabuslines.controller.user.UpdateUserRequest;
import com.varnabuslines.domain.Role;
import com.varnabuslines.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RolesAllowed("ROLE_ADMIN")
@RequestMapping("/admins")
public class AdminController
{
    private UserService userService;

    @PostMapping
    public ResponseEntity<CreateUserResponse> createAdmin(@RequestBody final CreateUserRequest adminDTO)
    {
        var user = userService.createAdmin(adminDTO.getEmail(), adminDTO.getPassword());
        return ResponseEntity.status(HttpStatus.CREATED).body(Mapper.map(user, CreateUserResponse.class));
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateAnyUser(@PathVariable final long id,
                                              @RequestBody final UpdateUserRequest request)
    {
        Optional<User> userOpt;
        try{
            userOpt = userService.updateAsAdmin(id, request.getEmail(), request.getPassword(), Role.valueOf(request.getRole()));

        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        if (userOpt.isEmpty())
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok().build();
    }
}
