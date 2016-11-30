package com.firstidea.garnet.web.brokerx.admin;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author gurjeet singh
 */
public class Encryption {

    static final Logger logger = LoggerFactory.getLogger(Encryption.class);

    public static String encrypt(String password) throws Exception {
        try {
            MessageDigest d = MessageDigest.getInstance("SHA");
            d.reset();
            d.update(password.getBytes());
            password = byteArrayToHexString(d.digest());
        } catch (NoSuchAlgorithmException ex) {
            logger.info(ex.getMessage());
            throw new Exception("Username or password is incorrect.");
        }
        return password;
    }

    private static String byteArrayToHexString(byte[] b) {
        String result = "";
        for (int i = 0; i < b.length; i++) {
            result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
        }
        return result;
    }
}
