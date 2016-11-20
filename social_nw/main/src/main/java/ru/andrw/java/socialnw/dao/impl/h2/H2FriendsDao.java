package ru.andrw.java.socialnw.dao.impl.h2;

import org.intellij.lang.annotations.Language;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

import ru.andrw.java.socialnw.dao.DaoException;
import ru.andrw.java.socialnw.dao.FriendsDao;
import ru.andrw.java.socialnw.model.Profile;
import ru.andrw.java.socialnw.model.enums.Countries;
import ru.andrw.java.socialnw.model.enums.FStatus;
import ru.andrw.java.socialnw.model.enums.Gender;
import ru.andrw.java.socialnw.model.enums.RowStatus;

/**
 * Created by john on 10/13/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
@SuppressWarnings("FieldCanBeLocal")
class H2FriendsDao implements FriendsDao {

    private final Supplier<Connection> supplier;
    private final int[][] statuses = new int[][]{
            {FStatus.UNSET.ordinal(),2,5},
            {1,3,5},
            {4,4,6}};
    private final String SPLITERATOR = ".";
    private final String SCHEMA_NAME = "PUBLIC";
    private final String PROFILE_TABLE = "PROFILE";
    private final String RELATION_TABLE = "RELATION";
    private final String RELATION_TABLE_NAME = SCHEMA_NAME+SPLITERATOR+RELATION_TABLE;
    private final String DELETE = "DELETE FROM "+RELATION_TABLE_NAME+" WHERE ((IDREQUESTER = ?) AND (IDREQUESTEE = ?) AND (? IS NOT NULL));";
    private final String PROFILE_TABLE_NAME = SCHEMA_NAME+SPLITERATOR+PROFILE_TABLE;
    private final String INSERT_OR_UPDATE = "MERGE INTO "+RELATION_TABLE_NAME+" (IDREQUESTER, IDREQUESTEE, STATUS) VALUES (?,?,?);";
    @Language("H2")
    private final String SELECT_STATUS = "SELECT STATUS FROM "+RELATION_TABLE_NAME+" WHERE ((IDREQUESTER = ?) AND (IDREQUESTEE = ?));";
    @Language("H2")
    private final String FRIEND_LIST =
            "SELECT P.ID, P.BIRTHDATE, P.CITY, P.COUNTRY, P.FIRSTNAME, P.LASTNAME, P.PHONE, P.PHOTO, P.REGDATE, P.SEX, P.STATUS " +
                    "FROM "+RELATION_TABLE_NAME+" A, "+PROFILE_TABLE_NAME+" P " +
                    "  JOIN "+RELATION_TABLE_NAME+" B ON A.IDREQUESTEE = B.IDREQUESTER " +
                    "WHERE ((A.IDREQUESTER = ?) AND (B.IDREQUESTEE = A.IDREQUESTER) " +
                    "       AND (A.STATUS = "+ RowStatus.FRIENDLY.ordinal()+")  AND (B.STATUS = A.STATUS) " +
                    "       AND (P.ID = B.IDREQUESTER)) " +
                    "ORDER BY ID ASC LIMIT ? OFFSET ?;";
    private final String FOLLOWEES =
            "SELECT P.ID, P.BIRTHDATE, P.CITY, P.COUNTRY, P.FIRSTNAME, P.LASTNAME, P.PHONE, P.PHOTO, P.REGDATE, P.SEX, P.STATUS " +
                    "FROM "+RELATION_TABLE_NAME+" A " +
                    "  LEFT JOIN "+RELATION_TABLE_NAME+" B ON A.IDREQUESTEE = B.IDREQUESTER " +
                    "  JOIN "+PROFILE_TABLE_NAME+" P ON P.ID = A.IDREQUESTEE " +
                    "WHERE ((A.IDREQUESTER = ?) AND (B.IDREQUESTEE IS NULL )) " +
                    "ORDER BY ID ASC LIMIT ? OFFSET ?;";
    private final String FOLLOWERS =
            "SELECT P.ID, P.BIRTHDATE, P.CITY, P.COUNTRY, P.FIRSTNAME, P.LASTNAME, P.PHONE, P.PHOTO, P.REGDATE, P.SEX, P.STATUS " +
                    "FROM "+RELATION_TABLE_NAME+" A " +
                    "  RIGHT JOIN "+RELATION_TABLE_NAME+" B ON A.IDREQUESTEE = B.IDREQUESTER " +
                    "  JOIN "+PROFILE_TABLE_NAME+" P ON P.ID = B.IDREQUESTER " +
                    "WHERE ((B.IDREQUESTEE = ?) AND (A.IDREQUESTER IS NULL )) " +
                    "ORDER BY ID ASC LIMIT ? OFFSET ?;";
    private final String BLACK_LIST =
            "SELECT P.ID, P.BIRTHDATE, P.CITY, P.COUNTRY, P.FIRSTNAME, P.LASTNAME, P.PHONE, P.PHOTO, P.REGDATE, P.SEX, P.STATUS " +
                    "FROM "+RELATION_TABLE_NAME+" A " +
                    "  JOIN "+PROFILE_TABLE_NAME+" P ON P.ID = A.IDREQUESTEE " +
                    "WHERE ((A.IDREQUESTER = ?) AND (A.STATUS = "+RowStatus.HOSTILE.ordinal()+")) " +
                    "ORDER BY ID ASC LIMIT 30 OFFSET 0";

    H2FriendsDao(Supplier<Connection> supplier) {this.supplier = supplier;}


    @Override
    public List<Profile> getFriendsSublist(Long requesterId, Integer offset, Integer limit) throws DaoException {
        return getProfiles(requesterId, offset, limit, FRIEND_LIST);
    }

    @Override
    public List<Profile> getSentSublist(Long requesterId, Integer offset, Integer limit) throws DaoException {
        return getProfiles(requesterId, offset, limit, FOLLOWEES);
    }

    @Override
    public List<Profile> getReceivedSublist(Long requesterId, Integer offset, Integer limit) throws DaoException {
        return getProfiles(requesterId, offset, limit, FOLLOWERS);
    }

    @Override
    public List<Profile> getBlockedSublist(Long requesterId, Integer offset, Integer limit) throws DaoException {
        return getProfiles(requesterId, offset, limit, BLACK_LIST);
    }

    @Override
    public Integer friendsStatus(Long requesterId,
                                 Long requesteeId) throws DaoException {
        if(requesterId.equals(requesteeId)) return FStatus.SELF.ordinal();
        int out;
        try (Connection con = getConnection();
             PreparedStatement ps1 = con.prepareStatement(SELECT_STATUS);
             PreparedStatement ps2 = con.prepareStatement(SELECT_STATUS)
        ) {
            ps1.setLong(1, requesterId);
            ps1.setLong(2, requesteeId);
            ps2.setLong(1, requesteeId);
            ps2.setLong(2, requesterId);

            ResultSet rs1 = ps1.executeQuery();
            ResultSet rs2 = ps2.executeQuery();

            out = statuses[rs1.next()?rs1.getInt(1):0][rs2.next()?rs2.getInt(1):0];

        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return out;
    }

    @Override
    public void friendUpdate(Long requesterId, Long requesteeId,
                             RowStatus status) throws DaoException {

        if(!statusValidator(status)) throw new DaoException("Status is not valid!");
        else try(Connection con = getConnection();
                 PreparedStatement ps = con
                         .prepareStatement(status
                                 .equals(RowStatus.UNDEFINED)?DELETE:INSERT_OR_UPDATE)){
            ps.setLong(1,requesterId);
            ps.setLong(2,requesteeId);
            ps.setInt(3,status.ordinal());

            if (ps.executeUpdate() == 0) {
                throw new SQLException("Creating relation failed, no rows affected.");
            }

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Connection getConnection() {
        return supplier.get();
    }

    // Private methods -----------------------------------------------------------------------

    private boolean statusValidator(RowStatus status) { return ((status !=null)); }

    private List<Profile> getProfiles(Long requesterId, Integer offset, Integer limit, String SQL) {
        List<Profile> profileList = new ArrayList<>();
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(SQL)
        ) {
            ps.setLong(1, requesterId);
            ps.setInt(2, limit);
            ps.setInt(3, offset);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                profileList.add(getProfile(rs));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return profileList;
    }

    private Profile getProfile(ResultSet rs) throws SQLException {
        Profile profile = new Profile();
        int i = 1;
        profile.setId(rs.getLong(i));
        profile.setBirthDate(rs.getTimestamp(++i))
                .setCity(rs.getString(++i))
                .setCountry(Countries.valueOf(rs.getString(++i)))
                .setFirstName(rs.getString(++i))
                .setLastName(rs.getString(++i))
                .setPhone(rs.getString(++i))
                .setPhoto(rs.getObject(++i, UUID.class))
                .setRegDate(rs.getTimestamp(++i))
                .setSex(Gender.valueOf(rs.getString(++i)))
                .setStatus(rs.getString(++i));
        return profile;
    }
}
