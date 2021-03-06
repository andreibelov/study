package ru.andrw.java.socialnw.dao.impl.h2;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

import com.diffplug.common.base.Errors;
import com.epam.courses.jf.dao.Dao;

import org.intellij.lang.annotations.Language;

import ru.andrw.java.socialnw.dao.DaoException;
import ru.andrw.java.socialnw.dao.UserProfileDao;
import ru.andrw.java.socialnw.model.Profile;
import ru.andrw.java.socialnw.model.auth.User;
import ru.andrw.java.socialnw.model.enums.Countries;
import ru.andrw.java.socialnw.model.enums.Gender;

import static ru.andrw.java.socialnw.util.UuidUtils.asBytes;
import static ru.andrw.java.socialnw.util.UuidUtils.asUuid;

/**
 * Created by john on 10/11/2016.
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
@SuppressWarnings("FieldCanBeLocal")
class H2UserProfileDao implements UserProfileDao, Dao {

    private final Supplier<Connection> supplier;
    private final String SPLITERATOR = ".";
    private final String SCHEMA_NAME = "PUBLIC";
    private final String USER_TABLE = "USER";
    private final String PROFILE_TABLE = "PROFILE";
    private final String USER_TABLE_NAME = SCHEMA_NAME+SPLITERATOR+USER_TABLE;
    private final String PROFILE_TABLE_NAME = SCHEMA_NAME+SPLITERATOR+PROFILE_TABLE;
    private final String INSERT_USER = "INSERT INTO "+USER_TABLE_NAME+" (ACCESSLEVEL, EMAIL, LOGIN, PASSWORD, DTYPE) VALUES (?, ?, ?, ?, ?);";
    private final String INSERT_PROFILE = "INSERT INTO "+PROFILE_TABLE_NAME+" (BIRTHDATE, CITY, COUNTRY, FIRSTNAME, LASTNAME, PHONE, PHOTO, REGDATE, SEX, STATUS, ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
    private final String REG_PROFILE = "INSERT INTO "+PROFILE_TABLE_NAME+" (FIRSTNAME, LASTNAME, REGDATE, ID) VALUES (?,?,?,?);";
    private final String SELECT = "SELECT t0.ID, t0.ACCESSLEVEL, t0.EMAIL, t0.LOGIN, t1.BIRTHDATE, t1.CITY, t1.COUNTRY, t1.FIRSTNAME, t1.LASTNAME, t1.PHONE, t1.PHOTO, t1.REGDATE, t1.SEX, t1.STATUS FROM "+USER_TABLE_NAME+" t0, "+PROFILE_TABLE_NAME+" t1 WHERE ((t1.ID = t0.ID) AND (t0.DTYPE = ?)) LIMIT ? OFFSET ?;";
    private final String SELECT_BY_ID = "SELECT t0.ID, t0.ACCESSLEVEL, t0.EMAIL, t0.LOGIN, t1.BIRTHDATE, t1.CITY, t1.COUNTRY, t1.FIRSTNAME, t1.LASTNAME, t1.PHONE, t1.PHOTO, t1.REGDATE, t1.SEX, t1.STATUS FROM "+USER_TABLE_NAME+" t0, "+PROFILE_TABLE_NAME+" t1 WHERE ((t0.ID = ?) AND ((t1.ID = t0.ID) AND (t0.DTYPE = ?)))";
    private final String SELECT_BY_EMAIL = "SELECT t0.ID, t0.ACCESSLEVEL, t0.EMAIL, t0.LOGIN, t1.BIRTHDATE, t1.CITY, t1.COUNTRY, t1.FIRSTNAME, t1.LASTNAME, t1.PHONE, t1.PHOTO, t1.REGDATE, t1.SEX, t1.STATUS FROM "+USER_TABLE_NAME+" t0, "+PROFILE_TABLE_NAME+" t1 WHERE ((t0.EMAIL = ?) AND ((t1.ID = t0.ID) AND (t0.DTYPE = ?)))";
//    private final String SEARCH_BY_NAME = "SELECT t0.ID, t0.ACCESSLEVEL, t0.EMAIL, t0.LOGIN, t1.BIRTHDATE, t1.CITY, t1.COUNTRY, t1.FIRSTNAME, t1.LASTNAME, t1.PHONE, t1.PHOTO, t1.REGDATE, t1.SEX, t1.STATUS FROM "+USER_TABLE_NAME+" t0, "+PROFILE_TABLE_NAME+" t1 WHERE (((t1.FIRSTNAME = ?) OR (t1.LASTNAME = ?)) AND ((t1.ID = t0.ID) AND (t0.DTYPE = ?)))";
    private final String SEARCH_BY_NAME =
        "SELECT * FROM " +
               "(SELECT t0.ID, t0.ACCESSLEVEL, t0.EMAIL, t0.LOGIN, t1.BIRTHDATE, t1.CITY, t1.COUNTRY, t1.FIRSTNAME, t1.LASTNAME, t1.PHONE, t1.PHOTO, t1.REGDATE, t1.SEX, t1.STATUS " +
                "FROM "+USER_TABLE_NAME+" t0, "+PROFILE_TABLE_NAME+" t1 " +
                    "WHERE ((LOWER(t1.FIRSTNAME) LIKE ? ESCAPE '')  AND ((t1.ID = t0.ID) AND (t0.DTYPE = ?))) " +
                "UNION " +
                "SELECT t0.ID, t0.ACCESSLEVEL, t0.EMAIL, t0.LOGIN, t1.BIRTHDATE, t1.CITY, t1.COUNTRY, t1.FIRSTNAME, t1.LASTNAME, t1.PHONE, t1.PHOTO, t1.REGDATE, t1.SEX, t1.STATUS " +
                    "FROM "+USER_TABLE_NAME+" t0, "+PROFILE_TABLE_NAME+" t1 " +
                    "WHERE ((LOWER(t1.LASTNAME) LIKE ? ESCAPE '')  AND ((t1.ID = t0.ID) AND (t0.DTYPE = ?)))) " +
                "ORDER BY ID LIMIT ? OFFSET ?";
    @Language("H2")
    private final String USER_UPDATE = "UPDATE "+USER_TABLE_NAME+" SET ACCESSLEVEL = ?, EMAIL=?, LOGIN=?, PASSWORD=CASEWHEN( ? IS NULL, PASSWORD,PASSWORD) WHERE ID=?;";
    private final String PROFILE_UPDATE = "UPDATE "+PROFILE_TABLE_NAME+" SET BIRTHDATE = ?, CITY = ?, COUNTRY = ?, FIRSTNAME = ?, LASTNAME = ?, PHONE = ?, PHOTO = ?, REGDATE = ?, SEX = ?, STATUS =? WHERE ID=?;";
    private final String PROFILE_DELETE = "DELETE FROM "+PROFILE_TABLE_NAME+" WHERE ID = ?;";
    private final String USER_DELETE = "DELETE FROM "+USER_TABLE_NAME+" WHERE ID = ?;";

    H2UserProfileDao(Supplier<Connection> supplier){
        this.supplier = supplier;
    }

    @Override
    public Profile regNewProfile(Profile profile) throws DaoException {
        if(!validator(profile.setId(0L),User.class)) throw new DaoException("Null field: "+Arrays
                .stream(User.class.getDeclaredFields())
                .peek(f -> f.setAccessible(true))
                .filter(Errors.rethrow().wrap((Field f) -> f.get(profile) == null))
                .findAny().get().getName());
        else try(Connection con = getConnection();
                 PreparedStatement ps1 = con.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement ps2 = con.prepareStatement(REG_PROFILE)
        ){

            prepareFirst(profile, ps1);
            ps1.setString(5,profile.getClass().getSimpleName());

            postInspection(ps1, "Creating profile failed, no rows affected.");

            try (ResultSet generatedKeys = ps1.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    profile.setId(generatedKeys.getLong(1));
                }
                else {
                    throw new SQLException("Creating profile failed, no ID obtained.");
                }
            }

            registerProfile(profile, ps2);
            postInspection(ps2,"Creating profile failed, no rows affected.");

            profile.setFirstName("NEW").setLastName("USER");

        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return profile;
    }

    @Override
    public Profile addUserProfile(Profile profile) throws DaoException {
        if(!validator(profile.setId(0L))) throw new DaoException("Null field: "+Arrays
                .stream(Profile.class.getDeclaredFields())
                .peek(f -> f.setAccessible(true))
                .filter(Errors.rethrow().wrap((Field f) -> f.get(profile) == null))
                .findAny().get().getName());
        else try(Connection con = getConnection();
                 PreparedStatement ps1 = con.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement ps2 = con.prepareStatement(INSERT_PROFILE)
        ){
            prepareFirst(profile, ps1);
            ps1.setString(5,profile.getClass().getSimpleName());
            postInspection(ps1, "Creating profile failed, no rows affected.");
            try (ResultSet generatedKeys = ps1.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    profile.setId(generatedKeys.getLong(1));
                }
                else {
                    throw new SQLException("Creating profile failed, no ID obtained.");
                }
            }
            prepareSecond(profile, ps2);
            postInspection(ps2,"Creating profile failed, no rows affected.");
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return profile;
    }

    @Override
    public List<Profile> getUserProfilesSubList(Integer offset, Integer limit) throws DaoException {
        List<Profile> profileList = new ArrayList<>();
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT)
        ) {
            ps.setString(1, Profile.class.getSimpleName());
            ps.setInt(2, limit);
            ps.setInt(3, offset);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                profileList.add(mapProfile(rs));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return profileList;
    }


    @Override
    public List<Profile> searchUserProfilesByName(String name) {
        List<Profile> profileList = new ArrayList<>();
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(SEARCH_BY_NAME)
        ) {

            int limit = 30;
            int offset = 0;
            ps.setString(1, name.toLowerCase()+"%");
            ps.setString(2, Profile.class.getSimpleName());
            ps.setString(3, name.toLowerCase()+"%");
            ps.setString(4, Profile.class.getSimpleName());
            ps.setInt(5, limit);
            ps.setInt(6, offset);
            System.out.println(ps);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                profileList.add(mapProfile(rs));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return profileList;
    }

    @Override
    public Optional<Profile> searchUserProfileByEmail(String email) {
        Profile profile = null;
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_BY_EMAIL)
        ) {
            ps.setString(1, email);
            ps.setString(2, Profile.class.getSimpleName());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                profile = mapProfile(rs);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return Optional.ofNullable(profile);
    }

    @Override
    public Optional<Profile> getUserProfileById(Long id) {
        Profile profile = null;
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_BY_ID)
        ) {
            ps.setLong(1, id);
            ps.setString(2, Profile.class.getSimpleName());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                profile = mapProfile(rs);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return Optional.ofNullable(profile);
    }

    @Override
    public void updateUserProfile(Profile profile) throws DaoException {
        if(!validator(profile)) throw new DaoException("Profile is not valid!");
        else try(Connection con = getConnection();
                 PreparedStatement ps1 = con.prepareStatement(USER_UPDATE);
                 PreparedStatement ps2 = con.prepareStatement(PROFILE_UPDATE)
        ){
            prepareFirst(profile, ps1);
            ps1.setLong(5,profile.getId());
            postInspection(ps1, "Updating profile failed, no rows affected.");
            prepareSecond(profile, ps2);
            postInspection(ps2, "Updating profile failed, no rows affected.");
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void deleteUserProfile(Long id) throws DaoException {
        try (Connection con = getConnection();
             PreparedStatement ps1 = con.prepareStatement(PROFILE_DELETE);
             PreparedStatement ps2 = con.prepareStatement(USER_DELETE)
        ) {
            ps1.setLong(1, id);
            postInspection(ps1, "Deleting profile failed, no rows affected.");
            ps2.setLong(1, id);
            postInspection(ps2, "Deleting profile failed, no rows affected.");
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Connection getConnection() {
        return supplier.get();
    }

    // Private methods ------------------------------------------------------------------

    private Profile mapProfile(ResultSet rs) throws SQLException {
        Profile profile = new Profile();
        profile.setId(rs.getLong(1))
                .setAccessLevel(rs.getInt(2))
                .setEmail(rs.getString(3))
                .setLogin(rs.getString(4));
        profile.setBirthDate(rs.getTimestamp(5))
                .setCity(rs.getString(6))
                .setCountry(Countries.valueOf(rs.getString(7)))
                .setFirstName(rs.getString(8))
                .setLastName(rs.getString(9))
                .setPhone(rs.getString(10))
                .setPhoto(rs.getObject(11, UUID.class))
                .setRegDate(rs.getTimestamp(12))
                .setSex(Gender.valueOf(rs.getString(13)))
                .setStatus(rs.getString(14));
        return profile;
    }

    private boolean validator(Object o, Class c) {
        return o != null && !Arrays
                .stream(c.getDeclaredFields())
                .peek(f -> f.setAccessible(true))
                .filter(Errors.rethrow().wrap((Field f) -> f.get(o) == null))
                .findAny().isPresent();
    }

    private boolean validator(Object o) {
        return validator(o,o.getClass());
    }

    private void prepareFirst(Profile profile, PreparedStatement ps1) throws SQLException {
        ps1.setInt(1,profile.getAccessLevel());
        ps1.setString(2,profile.getEmail());
        ps1.setString(3,profile.getLogin());
        ps1.setString(4,profile.getPassword());
    }

    private void prepareSecond(Profile profile, PreparedStatement ps2) throws SQLException {
        ps2.setTimestamp(1,new Timestamp(profile.getBirthDate().getTime()));
        ps2.setString(2,profile.getCity());
        ps2.setString(3,profile.getCountry().toString());
        ps2.setString(4,profile.getFirstName());
        ps2.setString(5,profile.getLastName());
        ps2.setString(6,profile.getPhone());
        ps2.setBytes(7,asBytes(profile.getPhoto()));
        ps2.setTimestamp(8,new Timestamp(profile.getRegDate().getTime()));
        ps2.setString(9,profile.getSex().toString());
        ps2.setString(10,profile.getStatus());
        ps2.setLong(11,profile.getId());

    }


    private void registerProfile(Profile profile,
                                 PreparedStatement ps2) throws SQLException {
        ps2.setString(1,"NEW");
        ps2.setString(2,"USER");
        ps2.setTimestamp(3,new Timestamp((new Date()).getTime()));
        ps2.setLong(4,profile.getId());
    }

    private void postInspection(PreparedStatement ps1,
                                String message) throws SQLException {
        if (ps1.executeUpdate() == 0) {
            throw new SQLException(message);
        }
    }
}
