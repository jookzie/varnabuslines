package com.varnabuslines.controller.user;

import com.varnabuslines.business.UserService;
import com.varnabuslines.controller.ListWrapper;
import com.varnabuslines.controller.Mapper;
import com.varnabuslines.controller.OnlyUsers;
import com.varnabuslines.domain.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
@Slf4j
public class UserController
{
    private final UserService userService;

    @RolesAllowed("ROLE_ADMIN")
    @GetMapping
    public ResponseEntity<ListWrapper<GetUserResponse>> getAll()
    {
        var array = Mapper.map(userService.get(), GetUserResponse[].class);
        return ResponseEntity.ok(new ListWrapper<>(array));
    }

    @OnlyUsers
    @GetMapping("{id}")
    public ResponseEntity<GetUserResponse> get(@PathVariable final long id)
    {
        Optional<User> opt = userService.get(id);
        if (opt.isEmpty())
            return ResponseEntity.notFound().build();
        var user = opt.get();
        return ResponseEntity.ok(Mapper.map(user, GetUserResponse.class));
    }

    @OnlyUsers
    @PutMapping("{id}")
    public ResponseEntity<GetUserResponse> updateSelf(@PathVariable final long id,
                                                      @RequestBody UpdateUserRequest request)
    {
        Optional<User> opt = userService.updateAsUser(id, request.getEmail(), request.getPassword());
        if (opt.isEmpty())
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok(Mapper.map(opt.get(), GetUserResponse.class));
    }

    @PostMapping
    public ResponseEntity<CreateUserResponse> create(@RequestBody final CreateUserRequest userDTO)
    {
        var user = userService.createUser(userDTO.getEmail(), userDTO.getPassword());
        return ResponseEntity.status(HttpStatus.CREATED).body(Mapper.map(user, CreateUserResponse.class));
    }

    @OnlyUsers
    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable final long id)
    {
        try
        {
            userService.delete(id);
        } catch (IllegalStateException e)
        {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.noContent().build();
    }
}
