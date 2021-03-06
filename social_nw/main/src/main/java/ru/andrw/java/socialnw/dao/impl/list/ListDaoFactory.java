package ru.andrw.java.socialnw.dao.impl.list;

import com.opencsv.CSVReader;
import org.apache.tomcat.util.security.ConcurrentMessageDigest;
import org.apache.tomcat.util.security.MD5Encoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.andrw.java.socialnw.dao.DaoFactory;
import ru.andrw.java.socialnw.dao.FriendsDao;
import ru.andrw.java.socialnw.dao.IMDao;
import ru.andrw.java.socialnw.dao.PostDao;
import ru.andrw.java.socialnw.dao.SectionDao;
import ru.andrw.java.socialnw.dao.TokensDao;
import ru.andrw.java.socialnw.dao.UserDao;
import ru.andrw.java.socialnw.dao.UserProfileDao;
import ru.andrw.java.socialnw.model.auth.User;
import ru.andrw.java.socialnw.model.Profile;
import ru.andrw.java.socialnw.model.enums.Countries;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
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

    private static final Logger logger = LoggerFactory
            .getLogger("ru.andrw.java.socialnw.dao.impl.list.ListDaoFactory");

    private UserProfileDao profileDao;
    private TokensDao tokensDao;
    private UserDao userDao;

    public ListDaoFactory(){

        AtomicLong al = new AtomicLong(0L);
        List<User> userList = new CopyOnWriteArrayList<>();
        String pass = MD5Encoder.encode(ConcurrentMessageDigest.digestMD5("admin".getBytes()));
        userList.add((new User()).setId(al.getAndIncrement())
                .setEmail("andrei.belov@mail.ru")
                .setLogin("andrei.belov")
                .setPassword(pass)
                .setAccessLevel(0));

        this.userDao = new ListUserDao(userList,al);

        AtomicLong counter = new AtomicLong(500L);

        String csvPath = "initial_profiles.csv";
        List<Profile> profileList = new CopyOnWriteArrayList<>();
        CSVReader reader = null;
        try {
            Optional<URL> csvFileUrl = ofNullable(getClass()
                    .getClassLoader()
                    .getResource(csvPath));
            if(csvFileUrl.isPresent())
                reader = new CSVReader(new FileReader(csvFileUrl.get().getFile()));
            else throw new IOException("File doesn't exist");
            String[] line;
            String pattern = "dd-MM-yyyy";
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            while ((line = reader.readNext()) != null) {
                Profile profile = (new Profile())
                        .setFirstName(line[1])
                        .setLastName(line[2])
                        .setBirthDate(format.parse(line[3]))
                        .setCountry(Countries.valueOf(line[4]))
                        .setCity(line[5]);
                profile.setEmail(line[6])
                        .setId(counter.getAndIncrement());
                profileList.add(profile);
            }
            reader.close();
        } catch (IOException | ParseException e) {
            logger.error("Error parsing init data!", e);
        }
        this.profileDao = new ListUserProfileDao(profileList,counter);

        Map<String, Long> tokensList = new ConcurrentHashMap<>();

        this.tokensDao = new ListTokensDao(tokensList);

    }

    @Override
    public UserDao getUserDao() {
        return userDao;
    }

    @Override
    public UserProfileDao getProfileDao() {
        return profileDao;
    }

    @Override
    public TokensDao getTokensDao() {
        return tokensDao;
    }

    @Override
    public FriendsDao getFriendsDao() {
        return null;
    }

    @Override
    public IMDao getIMDao() {
        return null;
    }

    @Override
    public PostDao getPostDao() {
        return null;
    }

    @Override
    public SectionDao getSectionDao() {
        return null;
    }
}
