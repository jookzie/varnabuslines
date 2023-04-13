package com.varnabuslines.domain;

import com.varnabuslines.business.BusinessLayerTest;
import com.varnabuslines.domain.exceptions.ValidationException;
import com.varnabuslines.domain.utils.PasswordHasher;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@BusinessLayerTest
class UserTest
{
    @Test
    void testConstructor()
    {
        assertDoesNotThrow(() ->
            new User(
            1L,
            "john.doe+flag@gmail.com",
            PasswordHasher.hash("password"),
            Role.USER));
    }

    @Test
    void setId_invalid()
    {
        var user = new User();
        assertThrows(ValidationException.class, () ->
                user.setId(-1L));
    }

    @Test
    void setEmail_valid()
    {
        var user = new User();
        assertDoesNotThrow(() -> user.setEmail("john.doe+flag@gmail.com"));
    }

    @Test
    void setEmail_invalid_atSymbol()
    {
        var user = new User();
        assertThrows(ValidationException.class,
                () -> user.setEmail("john.doe+flagATgmail.com"));
    }

    @Test
    void setEmail_invalid_badDomain()
    {
        var user = new User();
        assertThrows(ValidationException.class,
                () -> user.setEmail("john.doe+flag@gmailDOTcom"));
    }

    @Test
    void equals_valid()
    {
        var user1 = new User(
                1L,
                "john.doe+flag@gmail.com",
                "password",
                Role.USER);
        var user2 = new User(
                1L,
                "john.doe+flag@gmail.com",
                "password",
                Role.USER);
        assertEquals(user1, user2);
    }

    @Test
    void equals_invalid_byId()
    {
        var user1 = new User(
                1L,
                "john.doe+flag@gmail.com",
                "password",
                Role.USER);
        var user2 = new User(
                2L,
                "john.doe+flag@gmail.com",
                "password",
                Role.USER);
        assertNotEquals(user1, user2);
    }

    @Test
    void equals_invalid_byEmail()
    {
        var user1 = new User(
                1L,
                "john.doe+flag@gmail.com",
                "password",
                Role.USER);
        var user2 = new User(
                1L,
                "jane.doe+flag@gmail.com",
                "password",
                Role.USER);
        assertNotEquals(user1, user2);
    }

    @Test
    void hashcode_valid()
    {
        var user1 = new User(
                1L,
                "john.doe+flag@gmail.com",
                "password",
                Role.USER);
        var user2 = new User(
                1L,
                "john.doe+flag@gmail.com",
                "password",
                Role.USER);
        assertEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    void hashCode_invalid_byId()
    {
        var user1 = new User(
                1L,
                "john.doe+flag@gmail.com",
                "password",
                Role.USER);
        var user2 = new User(
                2L,
                "john.doe+flag@gmail.com",
                "password",
                Role.USER);
        assertNotEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    void hashCode_invalid_byEmail()
    {
        var user1 = new User(
                1L,
                "john.doe+flag@gmail.com",
                "password",
                Role.USER);
        var user2 = new User(
                1L,
                "jane.doe+flag@gmail.com",
                "password",
                Role.USER);
        assertNotEquals(user1.hashCode(), user2.hashCode());
    }
}