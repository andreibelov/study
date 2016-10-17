package ru.andrw.java.socialnw.dao.impl.h2;

import com.epam.courses.jf.dao.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import ru.andrw.java.socialnw.dao.DaoException;
import ru.andrw.java.socialnw.dao.UserDao;
import ru.andrw.java.socialnw.model.auth.User;

import static java.util.Optional.ofNullable;

/**
 * Created by john on 10/11/2016.
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */

@SuppressWarnings("FieldCanBeLocal")
class H2UserDao implements Dao, UserDao {

    private final Supplier<Connection> supplier;
    private final String SPLITERATOR = ".";
    private final String SCHEMA_NAME = "PUBLIC";
    private final String USER_TBL = "USER";
    private final String USER_TNAME = SCHEMA_NAME+SPLITERATOR+ USER_TBL;
    private final String SQL_INSERT = "INSERT INTO "+ USER_TNAME +" (ACCESSLEVEL, EMAIL, LOGIN, PASSWORD, DTYPE) VALUES (?,?,?,?,?);";
    private final String SQL_SELECT = "SELECT ID, ACCESSLEVEL, EMAIL, LOGIN  FROM "+ USER_TNAME +" LIMIT ? OFFSET ?;";
    private final String SQL_SELECT_BY_ID = "SELECT ID, ACCESSLEVEL, EMAIL, LOGIN  FROM "+ USER_TNAME +" WHERE ID=?;";
    private final String SQL_SELECT_BY_LOGIN = "SELECT ID, ACCESSLEVEL, EMAIL, LOGIN FROM "+ USER_TNAME +" WHERE LOGIN=?;";
    private final String SQL_SELECT_BY_EMAIL = "SELECT ID, ACCESSLEVEL, EMAIL, LOGIN FROM "+ USER_TNAME +" WHERE EMAIL=?;";
    private final String SQL_SELECT_FIND = "SELECT ID, ACCESSLEVEL, EMAIL, LOGIN FROM "+ USER_TNAME +" WHERE EMAIL=? AND PASSWORD=?;";
    private final String SQL_UPDATE = "UPDATE "+ USER_TNAME +" SET ACCESSLEVEL = ?, EMAIL=?, LOGIN=?, PASSWORD=? WHERE ID=?;";
    private final String SQL_UPDATE_PASS = "UPDATE "+ USER_TNAME +" SET PASSWORD=? WHERE ID=?;";
    private final String SQL_DELETE = "DELETE FROM "+ USER_TNAME +" WHERE ID = ?;";

    H2UserDao(Supplier<Connection> supplier){
        this.supplier = supplier;
    }

    @Override
    public Optional<User> findUser(String email, String password) throws DaoException {
        User user = null;
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(SQL_SELECT_FIND);
             ) {
            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = mapUser(rs);
            }

        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return ofNullable(user);
    }

    @Override
    public User addUser(User user) throws DaoException {
        if(!userValidator(user)) throw new DaoException("User is not valid!");
        else try(Connection con = getConnection();
                 PreparedStatement ps = con.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)){

            prepareUser(user,ps);
            ps.setString(5,user.getClass().getSimpleName());

            postInspection(ps, "Creating user failed, no rows affected.");

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getLong(1));
                }
                else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return user;
    }


    @Override
    public void deleteUser(Long userId) throws DaoException {
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(SQL_DELETE);
        ) {
            ps.setLong(1, userId);
            postInspection(ps, "Deleting user failed, no rows affected.");

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private void postInspection(PreparedStatement ps, String reason) throws SQLException {
        int affectedRows = ps.executeUpdate();

        if (affectedRows == 0) {
            throw new SQLException(reason);
        }
    }

    @Override
    public void changePassword(Long userId, String pass) throws DaoException {
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(SQL_UPDATE_PASS);
        ) {
            ps.setString(1,pass);
            ps.setLong(2,userId);

            postInspection(ps, "Changing password failed, no rows affected.");

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void updateUser(User user) throws DaoException {
        if(!userValidator(user)) throw new DaoException("User is not valid!");
        else try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(SQL_UPDATE);
        ) {
            prepareUser(user, ps);
            ps.setLong(5,user.getId());
            postInspection(ps, "Updating user failed, no rows affected.");

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<User> getUsersSubList(Integer offset, Integer limit)
            throws DaoException {

        List<User> userList = new ArrayList<>();


        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(SQL_SELECT);
        ) {
            ps.setInt(1, limit);
            ps.setInt(2, offset);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                userList.add(mapUser(rs));
            }

        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return userList;
    }

    @Override
    public Optional<User> getUserById(Long userId) throws DaoException {
        User user = null;
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(SQL_SELECT_BY_ID);
        ) {
            ps.setLong(1, userId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = mapUser(rs);
            }

        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return ofNullable(user);
    }

    @Override
    public Optional<User> getUserByLogin(String login) throws DaoException {
        return getUserByString(login, SQL_SELECT_BY_LOGIN);
    }

    @Override
    public Optional<User> getUserByEmail(String email) throws DaoException {
        return getUserByString(email, SQL_SELECT_BY_EMAIL);
    }

    @Override
    public Connection getConnection() {
        return supplier.get();
    }

    // Private methods ------------------------------------------------------------------

    private Optional<User> getUserByString(String string, String SQL) {
        User user = null;
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(SQL);
        ) {
            ps.setString(1, string);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = mapUser(rs);
            }

        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return ofNullable(user);
    }

    private User mapUser(ResultSet rs) throws SQLException {
        User user;
        user = (new User())
                .setId(rs.getLong(1))
                .setAccessLevel(rs.getInt(2))
                .setEmail(rs.getString(3))
                .setLogin(rs.getString(4));
        return user;
    }

    private boolean userValidator(User user){
        return ofNullable(user).filter(u -> u != null)
                .filter(u -> u.getLogin() != null)
                .filter(u -> u.getPassword() != null)
                .filter(u -> u.getEmail() != null)
                .isPresent();
    }

    private void prepareUser(User user, PreparedStatement ps) throws SQLException {
        ps.setInt(1,user.getAccessLevel());
        ps.setString(2,user.getEmail());
        ps.setString(3,user.getLogin());
        ps.setString(4,user.getPassword());
    }

}
