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
    private final String DB_NAME = "TEST";
    private final String SCHEMA_NAME = "PUBLIC";
    private final String TABLE_NAME = "USER";
    private final String FULL_TABLE_NAME = DB_NAME+SPLITERATOR+SCHEMA_NAME+SPLITERATOR+TABLE_NAME;
    private final String SQL_INSERT = "INSERT INTO "+FULL_TABLE_NAME+" (ACCESSLEVEL, EMAIL, LOGIN, PASSWORD, DTYPE) VALUES (?,?,?,?,?);";
    private final String SQL_SELECT = "SELECT ID, ACCESSLEVEL, EMAIL, LOGIN  FROM "+FULL_TABLE_NAME+" LIMIT ? OFFSET ?;";
    private final String SQL_SELECT_BY_ID = "SELECT ACCESSLEVEL, EMAIL, LOGIN  FROM "+FULL_TABLE_NAME+" WHERE ID=?;";
    private final String SQL_SELECT_BY_LOGIN = "SELECT ID, ACCESSLEVEL, EMAIL FROM "+FULL_TABLE_NAME+" WHERE LOGIN=?;";
    private final String SQL_SELECT_BY_EMAIL = "SELECT ID, ACCESSLEVEL, LOGIN FROM "+FULL_TABLE_NAME+" WHERE EMAIL=?;";
    private final String SQL_SELECT_FIND = "SELECT ID, ACCESSLEVEL, LOGIN FROM "+FULL_TABLE_NAME+" WHERE EMAIL=? AND PASSWORD=?;";
    private final String SQL_UPDATE = "UPDATE "+FULL_TABLE_NAME+" SET ACCESSLEVEL = ?, EMAIL=?, LOGIN=?, PASSWORD=? WHERE ID=?;";
    private final String SQL_UPDATE_PASS = "UPDATE "+FULL_TABLE_NAME+" SET PASSWORD=? WHERE ID=?;";
    private final String SQL_DELETE = "DELETE FROM "+FULL_TABLE_NAME+" WHERE ID = ?;";

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
                user = new User();
                user.setId(rs.getLong(1))
                        .setAccessLevel(rs.getInt(2))
                        .setEmail(email)
                        .setLogin(rs.getString(3))
                ;
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

            ps.setInt(1,user.getAccessLevel());
            ps.setString(2,user.getEmail());
            ps.setString(3,user.getLogin());
            ps.setString(4,user.getPassword());
            ps.setString(5,user.getClass().getSimpleName());

            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

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
            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Deleting user failed, no rows affected.");
            }

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public void changePassword(Long userId, String pass) throws DaoException {
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(SQL_UPDATE_PASS);
        ) {
            ps.setString(1,pass);
            ps.setLong(2,userId);


            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Deleing user failed, no rows affected.");
            }

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
            ps.setInt(1,user.getAccessLevel());
            ps.setString(2,user.getEmail());
            ps.setString(3,user.getLogin());
            ps.setString(4,user.getPassword());
            ps.setLong(5,user.getId());

            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Deleing user failed, no rows affected.");
            }

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
                userList.add((new User())
                        .setId(rs.getLong(1))
                        .setAccessLevel(rs.getInt(2))
                        .setEmail(rs.getString(3))
                        .setLogin(rs.getString(4))
                );
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
                user = (new User())
                        .setId(userId)
                        .setAccessLevel(rs.getInt(1))
                        .setEmail(rs.getString(2))
                        .setLogin(rs.getString(3));
            }

        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return ofNullable(user);
    }

    @Override
    public Optional<User> getUserByLogin(String login) throws DaoException {
        User user = null;
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(SQL_SELECT_BY_LOGIN);
        ) {
            ps.setString(1, login);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = (new User())
                        .setId(rs.getLong(1))
                        .setAccessLevel(rs.getInt(2))
                        .setEmail(rs.getString(3))
                        .setLogin(login);
            }

        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return ofNullable(user);
    }

    @Override
    public Optional<User> getUserByEmail(String email) throws DaoException {
        User user = null;
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(SQL_SELECT_BY_EMAIL);
        ) {
            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = (new User())
                        .setId(rs.getLong(1))
                        .setAccessLevel(rs.getInt(2))
                        .setEmail(email)
                        .setLogin(rs.getString(3));
            }

        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return ofNullable(user);
    }

    @Override
    public Connection getConnection() {
        return supplier.get();
    }

    private boolean userValidator(User user){
        return ofNullable(user).filter(u -> u != null)
                .filter(u -> u.getLogin() != null)
                .filter(u -> u.getPassword() != null)
                .filter(u -> u.getEmail() != null)
                .isPresent();
    }


}
