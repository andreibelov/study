package com.example.chat.dao.impl.list;

import com.example.chat.dao.ChatMessageDao;
import com.example.chat.dao.DaoFactory;
import com.example.chat.dao.UserDao;
import com.example.chat.dao.UserProfileDao;
import com.example.chat.model.User;
import com.example.chat.model.UserProfile;
import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

import static java.util.Optional.ofNullable;

/**
 * Created by john on 9/22/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
public class ListDaoFactory implements DaoFactory {

    private UserDao userDao;
    private ChatMessageDao messageDao;
    private UserProfileDao profileDao;

    public ListDaoFactory(){

        AtomicLong al = new AtomicLong(0L);
        List<User> userList = new CopyOnWriteArrayList<User>();
        userList.add((new User()).setId(al.get())
                        .setEmail("andrei.belov@mail.ru")
                        .setLogin("andrei.belov@mail.ru")
                        .setPassword("admin"));

        this.userDao = new ListUserDao(userList,al);

        AtomicLong counter = new AtomicLong(500L);

        String csvPath = "initial_profiles.csv";
        List<UserProfile> profileList = new CopyOnWriteArrayList<>();
        CSVReader reader = null;
        try {
            Optional<URL> csvFileUrl = ofNullable(getClass()
                    .getClassLoader()
                    .getResource(csvPath));
            if(csvFileUrl.isPresent())
                reader = new CSVReader(new FileReader(csvFileUrl.get().getFile()));
            else throw new IOException("File doesn't exist");
            String[] line;
            while ((line = reader.readNext()) != null) {
                profileList.add((new UserProfile())
                        .setName(line[0])
                        .setLastName(line[1])
                        .setBirthDate(line[2])
                        .setCountry(line[3])
                        .setCity(line[4])
                        .setEmail(line[5])
                        .setId(counter.getAndIncrement()));
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.profileDao = new ListUserProfileDao(profileList,counter);
    }

    @Override
    public UserDao getUserDao() {
        return userDao;
    }

    @Override
    public ChatMessageDao getChatMessageDao() {
        return messageDao;
    }

    @Override
    public UserProfileDao getProfileDao() { return profileDao; }
}
