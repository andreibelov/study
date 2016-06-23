package ru.belov.java.proj4.webservlet.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by john on 6/22/2016.
 *
 */

public class Auth {

        public static void main(String[] args)
        {
            String data = "This is a message to be digested using MD5";
            String pass;
            MessageDigest messageDigest;
            try {
                messageDigest = MessageDigest.getInstance("MD5");
                messageDigest.update(data.getBytes());
                byte[] digest = messageDigest.digest();
                StringBuffer stringBuffer = new StringBuffer();
                for (byte bytes : digest) {
                    stringBuffer.append(String.format("%02x", bytes & 0xff));
                }
                String cast = stringBuffer.toString();
            } catch (NoSuchAlgorithmException exception) {
                // TODO Auto-generated catch block
                exception.printStackTrace();
            }
        }
}
