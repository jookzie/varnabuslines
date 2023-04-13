package com.varnabuslines.domain;

import com.varnabuslines.business.BusinessLayerTest;
import com.varnabuslines.domain.utils.PasswordHasher;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@BusinessLayerTest
class PasswordHasherTest
{

    @Test
    void hash_verify()
    {
        var hash = PasswordHasher.hash("123456");
        assertTrue(PasswordHasher.verify("123456", hash));
    }

    @Test
    void hash_verify_badHash()
    {
        var hash = PasswordHasher.hash("123456") + '7';
        assertFalse(PasswordHasher.verify("1234567", hash));
    }

    @Test
    void hash_verify_badPassword()
    {
        var hash = PasswordHasher.hash("123456");
        assertFalse(PasswordHasher.verify("1234567", hash));
    }

    @Test
    void hash_verify_badPasswordAndHash()
    {
        var hash = PasswordHasher.hash("123456") + '7';
        assertFalse(PasswordHasher.verify("1234567", hash));
    }

    @Test
    void hash_is_not_password()
    {
        var hash = PasswordHasher.hash("123456");
        assertNotEquals("123456", hash);
    }
}