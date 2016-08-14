package ru.belov.study.proj5.core;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by john on 7/1/2016.
 *
 * This class provides static method to encrypt
 */
public class MD5Encrypter {


    private static MessageDigest md5;

    static {
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static String encrypt(String password) {

        md5.reset();

        byte[] bs = md5.digest(password.getBytes());

        StringBuilder stringBuilder = new StringBuilder();

        String hexVal;

        //hex encode the digest
        for (byte b : bs) {
            hexVal = Integer.toHexString(0xFF & b);
            if (hexVal.length() == 1)
                stringBuilder.append("0");
            stringBuilder.append(hexVal);
        }

        return stringBuilder.toString();
    }

}
