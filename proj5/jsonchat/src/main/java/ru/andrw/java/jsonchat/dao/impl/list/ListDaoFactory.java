package ru.andrw.java.jsonchat.dao.impl.list;

import com.opencsv.CSVReader;
import ru.andrw.java.jsonchat.dao.ChatMessageDao;
import ru.andrw.java.jsonchat.dao.DaoFactory;
import ru.andrw.java.jsonchat.dao.UserDao;
import ru.andrw.java.jsonchat.dao.UserProfileDao;
import ru.andrw.java.jsonchat.model.User;
import ru.andrw.java.jsonchat.model.UserProfile;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

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
