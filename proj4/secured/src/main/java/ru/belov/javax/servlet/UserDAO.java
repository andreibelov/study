package ru.belov.javax.servlet;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Hashtable;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.Map.Entry;

/**
 * Created by john on 6/23/2016.
 *
 */

interface UserDAO {

    static User find(final String login, final String password) throws IOException, NoSuchElementException {

        Long uid;
        String username;
        String encriptedPass = getHash(password);
        String pass;

        String uidsFileName = "uids";
        Properties uids = new Properties();
        uids.load(UserDAO.class.getClassLoader().getResourceAsStream(uidsFileName));

        Hashtable<String, Long> users  = new Hashtable<String, Long>();
        for(Entry<Object, Object> entry : uids.entrySet()) {
            users.put((String) entry.getKey(),(Long) entry.getValue());
        }

        String passwdFileName = "shadow";
        Properties shadow = new Properties();
        shadow.load(UserDAO.class.getClassLoader().getResourceAsStream(passwdFileName));

        Hashtable<Long, String> passwd  = new Hashtable<Long, String>();
        for(Entry<Object, Object> entry : shadow.entrySet()) {
            passwd.put((Long) entry.getKey(),(String) entry.getValue());
        }

        uid = users.get(login);
        if (uid == null) throw new NoSuchElementException();
        pass = passwd.get(uid);
        if (pass == null)throw new NoSuchElementException(); else {
            if (pass.equals(encriptedPass)) return User.getUserNameById(uid);
        }


        return null;
    }

    static String getHash(String pass) {
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(pass.getBytes());
            byte[] digest = messageDigest.digest();
            StringBuilder sb = new StringBuilder();
            for (byte bytes : digest) {
                sb.append(String.format("%02x", bytes & 0xff));
            }
            String hash = sb.toString();

        } catch (NoSuchAlgorithmException exception) {
            // TODO Auto-generated catch block
            exception.printStackTrace();
        }
            return null;
    }
}
