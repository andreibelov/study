package ru.andrw.java.socialnw.dao.impl.list;

import com.opencsv.CSVReader;
import org.apache.tomcat.util.security.ConcurrentMessageDigest;
import org.apache.tomcat.util.security.MD5Encoder;
import ru.andrw.java.socialnw.dao.DaoFactory;
import ru.andrw.java.socialnw.dao.UserDao;
import ru.andrw.java.socialnw.dao.UserProfileDao;
import ru.andrw.java.socialnw.model.User;
import ru.andrw.java.socialnw.model.UserProfile;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

import static java.util.Optional.ofNullable;

/**
 * Created by john on 9/25/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
public class ListDaoFactory implements DaoFactory {

    private UserProfileDao profileDao;
    private UserDao userDao;

    public ListDaoFactory(){

        AtomicLong al = new AtomicLong(0L);
        List<User> userList = new CopyOnWriteArrayList<>();
        String pass = MD5Encoder.encode(ConcurrentMessageDigest.digestMD5("admin".getBytes()));
        userList.add((new User()).setId(al.getAndIncrement())
                .setEmail("andrei.belov@mail.ru")
                .setLogin("andrei.belov")
                .setPassword(pass));

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
    public UserProfileDao getProfileDao() {
        return profileDao;
    }
}
