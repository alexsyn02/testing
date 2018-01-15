package ua.com.testing.service;

import org.apache.commons.codec.digest.DigestUtils;

public interface PasswordHashingService {

    static String hashPassword(String password) {
        return DigestUtils.md5Hex(password);
    }
}
