package ru.andrw.java.socialnw.dao.impl.h2;

import com.opencsv.CSVReader;

import org.apache.tomcat.util.security.ConcurrentMessageDigest;
import org.apache.tomcat.util.security.MD5Encoder;
import org.junit.Before;
import org.junit.Test;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import ru.andrw.java.socialnw.dao.DaoException;
import ru.andrw.java.socialnw.dao.UserProfileDao;
import ru.andrw.java.socialnw.model.Profile;
import ru.andrw.java.socialnw.model.auth.User;
import ru.andrw.java.socialnw.model.enums.Countries;
import ru.andrw.java.socialnw.model.enums.Gender;
import ru.andrw.java.socialnw.pooling.ConnectionPool;

import static java.util.Optional.ofNullable;
import static org.junit.Assert.*;

/**
 * ru.andrw.java.socialnw.dao.impl.h2.H2FriendsDao Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre></pre>
 */
public class H2FriendsDaoTest {

    private static final String DB_PROPERTIES_FILE_NAME = "h2.db.properties";

    private static ConnectionPool connectionPool;
    private UserProfileDao profileDao;
    private H2DaoFactory daoFactory;

    private final String CERT_FILE_NAME = "cert.cer";
    private String email = "anya@gmail.com";
    private String username = "anya.petrova";
    private String password = "encoded_string";
    private String phone = "+79110129117";
    private String firstName = "Anya";
    private String lastName = "Petrova";
    private Gender sex = Gender.FEMALE;


    @Before
    public void setUp() throws Exception {

        URL dbPropertiesURL = H2UserDao.class.getClassLoader()
                .getResource(DB_PROPERTIES_FILE_NAME);

        final String dbPropertiesFilePath = ( dbPropertiesURL != null) ?
                dbPropertiesURL.getPath() : "";
        connectionPool = ConnectionPool.create(dbPropertiesFilePath);
        daoFactory = new H2DaoFactory(connectionPool);
        profileDao = daoFactory.getProfileDao();

        String pattern = "dd-mm-yyyy";
        SimpleDateFormat format = new SimpleDateFormat(pattern);

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("samplePU");
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction et = em.getTransaction();

        Profile profile = (new Profile())
                .setFirstName(firstName)
                .setLastName(lastName)
                .setPhone("+79112935537")
                .setSex(sex)
                .setCountry(Countries.valueOf("US"))
                .setCity("Brooklyn")
                .setRegDate(new Timestamp((new Date().getTime())))
                .setStatus("Welcome!")
                .setBirthDate(format.parse("01-06-1989"))
                .setPhoto(UUID.nameUUIDFromBytes(username.getBytes()));
        profile.setLogin(username)
                .setEmail(email)
                .setPassword(encode(password));
        profile = profileDao.addUserProfile(profile);

        System.out.println(profile);

        et = em.getTransaction();
        et.begin();

        String csvPath = "initial_profiles.csv";
        CSVReader reader = null;
        String[] line;
        try {
            Optional<URL> csvFileUrl = ofNullable(getClass()
                    .getClassLoader()
                    .getResource(csvPath));
            if(csvFileUrl.isPresent())
                reader = new CSVReader(new FileReader(csvFileUrl.get().getFile()));
            else throw new IOException("File doesn't exist");

            while ((line = reader.readNext()) != null) {
                profile = (new Profile())
                        .setBirthDate(format.parse(line[3]))
                        .setCity(line[5])
                        .setCountry(Countries.valueOf(line[4]))
                        .setFirstName(line[1])
                        .setLastName(line[2])
                        .setPhone("+7-800-200-00-00")
                        .setPhoto(UUID.randomUUID())
                        .setRegDate(new Timestamp((new Date().getTime())))
                        .setSex(Gender.UNDEFINED)
                        .setStatus("=^.^=");
                profile.setAccessLevel(3)
                        .setEmail(line[6])
                        .setLogin(line[1].toLowerCase()+"."+line[2].toLowerCase())
                        .setPassword(encode(line[1].toLowerCase()+"."+line[2].toLowerCase()));
                em.persist(profile);
            }
            reader.close();
        } catch (DaoException ex){
            ex.printStackTrace();
        }

//        profile = (new Profile())
//                .setFirstName("Andrei")
//                .setLastName("Belov")
//                .setPhone("+79112935537");
//        profile.setEmail("andrei.belov@mail.ru");
//        em.persist(profile);
//
//        User user = (new User())
//                .setLogin("login")
//                .setPassword("pass")
//                .setEmail("mail")
//                .setAccessLevel(3);
//
//        em.persist(user);
        et.commit();
    }

    /**
     * Method: getFriendsSublist
     */
    @Test
    public void getFriendsSublist() throws Exception {
        List<Profile> profiles = profileDao.searchUserProfilesByName("o");
        System.out.println("List size is: "+profiles.size());
        for (Profile p :
                profiles) {
            System.out.println(p);
        }

    }

    /**
     * Method: friendsStatus
     */
    @Test
    public void friendsStatus() throws Exception {
        //TODO: Test goes here...

    }

    // Private methods ----------------------------------------------------------------------

    private static String encode(String password) {
        return MD5Encoder.encode(ConcurrentMessageDigest.digestMD5(password.getBytes()));
    }

}