package com.finmanager.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptionUtil implements PasswordEncoder {
    private static Logger logger = LoggerFactory.getLogger(EncryptionUtil.class);

    public EncryptionUtil() {
    }

    @Override
    public String encode(CharSequence sequence) {
        String plainTextPassword = (String) sequence;
        if (plainTextPassword == null) {
            return null;
        }

        String algorithm = "SHA";
        byte[] plainText = plainTextPassword.getBytes();

        MessageDigest md;

        try {
            md = MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            logger.error("Password encrypt exception. Message: {}", e.getMessage());
            return null;
        }

        md.reset();
        md.update(plainText);
        byte[] encodedPassword = md.digest();
        StringBuilder encryptedPassword = new StringBuilder();

        for (byte b : encodedPassword) {
            if ((b & 0xff) < 0x10) {
                encryptedPassword.append("0");
            }

            encryptedPassword.append(Long.toString(b & 0xff, 16));
        }
        return encryptedPassword.toString();
    }

    @Override
    public boolean matches(CharSequence existingPassword, String dbPassword) {
        if (dbPassword != null && dbPassword.length() != 0) {
            return encode(existingPassword).equals(dbPassword);
        } else {
            logger.warn("Empty encoded password");
            return false;
        }
    }
}
