package com.varnabuslines.business;

import com.varnabuslines.domain.Constraints;
import com.varnabuslines.domain.Role;
import com.varnabuslines.domain.User;
import com.varnabuslines.persistence.BusLineRepo;
import com.varnabuslines.persistence.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@BusinessLayerTest
class UserServiceTest
{
    private static final List<User> mockUsers = List.of(
            new User(
                0L,
                "test@test.com",
                "password",
                Role.USER),
            new User(
                1L,
                "test2@test.com",
                "password2",
                Role.USER)
    );

    private static final UserRepo mockRepo = mock(UserRepo.class);

    private static final BusLineRepo mockBusLineRepo = mock(BusLineRepo.class);

    private static final UserService service = new UserService(mockRepo, mockBusLineRepo);


    @BeforeEach
    void setup()
    {
        when(mockRepo.findAll()).thenReturn(mockUsers);
        when(mockRepo.findById(mockUsers.get(0).getId())).thenReturn(Optional.of(mockUsers.get(0)));
        when(mockRepo.findById(mockUsers.get(1).getId())).thenReturn(Optional.of(mockUsers.get(1)));
        when(mockRepo.findByEmail(mockUsers.get(0).getEmail())).thenReturn(Optional.of(mockUsers.get(0)));
        when(mockRepo.findByEmail(mockUsers.get(1).getEmail())).thenReturn(Optional.of(mockUsers.get(1)));
        when(mockRepo.existsByEmail(mockUsers.get(0).getEmail())).thenReturn(true);
        when(mockRepo.existsByEmail(mockUsers.get(1).getEmail())).thenReturn(true);
        when(mockRepo.save(any(User.class))).thenAnswer(i -> i.getArguments()[0]);
    }

    @Test
    void getUsers()
    {
        var actual = (Collection<User>) service.get();
        assertTrue(actual.containsAll(mockUsers));
    }

    @Test
    void getById()
    {
        var expected = mockUsers.get(0);
        var actual = service.get(expected.getId());
        assertTrue(actual.isPresent());
        assertEquals(actual.get(), expected);
    }

    @Test
    void createUser()
    {
        var actual = service.createUser(
                mockUsers.get(0).getEmail(),
                mockUsers.get(0).getHashedPassword());

        assertAll("Should have the same email, and the role of User",
            () -> assertEquals(Role.USER, actual.getRole()),
            () -> assertEquals(mockUsers.get(0).getEmail(), actual.getEmail())
        );
    }

    @Test
    void createAdmin()
    {
        var actual = service.createAdmin(
                mockUsers.get(1).getEmail(),
                mockUsers.get(1).getHashedPassword());

        assertAll("Should have the same email, and the role of Admin",
                () -> assertEquals(Role.ADMIN, actual.getRole()),
                () -> assertEquals(mockUsers.get(1).getEmail(), actual.getEmail())
        );
    }

    @Test
    void updateAsUser()
    {
        var user = new User(
                mockUsers.get(0).getId(),
                mockUsers.get(0).getEmail(),
                mockUsers.get(0).getHashedPassword(),
                Role.USER);

        var newUser = service.updateAsUser(
                user.getId(),
                'a' + mockUsers.get(0).getEmail(),
                mockUsers.get(0).getHashedPassword());

        assertAll("The method should return the user and have new email",
                () -> assertTrue(newUser.isPresent()),
                () -> assertEquals('a' + user.getEmail(), newUser.get().getEmail())
        );
    }

    @Test
    void updateAsAdmin()
    {
        var user = new User(
                mockUsers.get(0).getId(),
                mockUsers.get(0).getEmail(),
                mockUsers.get(0).getHashedPassword(),
                Role.USER);

        var newRole = Role.ADMIN;
        var newEmail = 'a' + mockUsers.get(0).getEmail();

        var newUser = service.updateAsAdmin(
                user.getId(),
                newEmail,
                mockUsers.get(0).getHashedPassword(),
                newRole);

        assertAll("The method should return the user and have new email and role",
                () -> assertTrue(newUser.isPresent()),
                () -> assertEquals(newUser.get().getEmail(), newEmail),
                () -> assertEquals(newUser.get().getRole(), newRole)
        );
    }

    @Test
    void delete()
    {
        assertDoesNotThrow(() -> service.delete(mockUsers.get(0).getId()));
    }

    @Test
    void delete_shouldNotThrow_onNotFound()
    {
        when(mockRepo.findAllByRole(Role.ADMIN)).thenReturn(mockUsers);
        assertDoesNotThrow(() -> service.delete(-1L));
    }

    @Test
    void delete_lastAdmin_shouldThrow()
    {
        when(mockRepo.findAllByRole(Role.ADMIN)).thenReturn(List.of(mockUsers.get(0)));
        long id = anyLong();
        assertThrows(IllegalStateException.class,
                () -> service.delete(id));
    }

    @Test
    void delete_twoAdmins_shouldNotThrow()
    {
        when(mockRepo.findAllByRole(Role.ADMIN)).thenReturn(mockUsers);
        assertDoesNotThrow(() -> service.delete(mockUsers.get(0).getId()));
    }

    private Method getValidatePasswordMethod() throws NoSuchMethodException
    {
        Method validatePassword = UserService.class.getDeclaredMethod("validatePassword", String.class);
        validatePassword.setAccessible(true);
        return validatePassword;
    }

    @Test
    void validatePassword_lowerBound() throws NoSuchMethodException
    {
        Method validatePassword = getValidatePasswordMethod();

        var password = getStringOfSize(Constraints.MIN_PASSWORD_LENGTH, 'a');

        assertDoesNotThrow(() -> validatePassword.invoke(service, password));

        validatePassword.setAccessible(false);
    }
    @Test
    void validatePassword_upperBound() throws NoSuchMethodException
    {
        Method validatePassword = getValidatePasswordMethod();

        var password = getStringOfSize(Constraints.MAX_PASSWORD_LENGTH, 'a');

        assertDoesNotThrow(() -> validatePassword.invoke(service, password));

        validatePassword.setAccessible(false);
    }

    private String getStringOfSize(int size, char c)
    {
        int i = 0;
        StringBuilder builder = new StringBuilder();
        while(i < size){
            builder.append(c);
            i++;
        }
        return builder.toString();
    }

    @Test
    void getStringOfSize_pass()
    {
        var expectedString = "aaaaaaaa";
        var actualString = getStringOfSize(8, 'a');
        assertEquals(expectedString, actualString);
    }

    @Test
    void getStringOfSize_Zero()
    {
        var expectedString = "";
        var actualString = getStringOfSize(0, 'a');
        assertEquals(expectedString, actualString);
    }

    @Test
    void getStringOfSize_Negative()
    {
        var expectedString = "";
        var actualString = getStringOfSize(-1, 'a');
        assertEquals(expectedString, actualString);
    }
}