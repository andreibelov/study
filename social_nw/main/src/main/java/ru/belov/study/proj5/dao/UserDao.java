package ru.belov.study.proj5.dao;

import ru.belov.study.proj5.model.account.User;

import java.util.List;
import java.util.Optional;

/**
 * This interface represents a contract for a DAO for the {@link User} model.
 * Note that all methods which returns the {@link User} from the DB, will not
 * fill the model with the password, due to security reasons.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
public interface UserDao {

    // Actions ------------------------------------------------------------------------------------

    /**
     * Returns the user from the database matching the given ID, otherwise null.
     * @param id The ID of the user to be returned.
     * @return The Optional<User> from the database matching the given ID or Optional.empty() if not found.
     * @throws DaoException If something fails at database level.
     */
    Optional<User> find(Long id) throws DaoException;

    /**
     * Returns the user from the database matching the given email and password, otherwise null.
     * @param login The login of the user to be returned.
     * @param password The password of the user to be returned.
     * @return The user from the database matching the given email and password, otherwise null.
     * @throws DaoException If something fails at database level.
     */
    Optional<User> find(String login, String password) throws DaoException;

    /**
     * Returns a list of all users from the database ordered by user ID. The list is never null and
     * is empty when the database does not contain any user.
     * @return A list of all users from the database ordered by user ID.
     * @throws DaoException If something fails at database level.
     */
    List<User> listAll() throws DaoException;

    /**
     * Adds the given user in the database. The user ID must be null, otherwise it will throw
     * IllegalArgumentException. After creating, the DAO will set the obtained ID in the given user.
     * @param user The user to be created in the database.
     * @throws IllegalArgumentException If the user ID is not null.
     * @throws DaoException If something fails at database level.
     */
    void add(User user) throws IllegalArgumentException, DaoException;

    /**
     * Update the given user in the database. The user ID must not be null, otherwise it will throw
     * IllegalArgumentException. Note: the password will NOT be updated. Use changePassword() instead.
     * @param user The user to be updated in the database.
     * @throws IllegalArgumentException If the user ID is null.
     * @throws DaoException If something fails at database level.
     */
    void update(User user) throws IllegalArgumentException, DaoException;

    /**
     * Delete the given user from the database. After deleting, the DAO will set the ID of the given
     * user to null.
     * @param user The user to be deleted from the database.
     * @throws DaoException If something fails at database level.
     */
    void delete(User user) throws DaoException;

    /**
     * Returns true if the given email address exist in the database.
     * @param email The email address which is to be checked in the database.
     * @return True if the given email address exist in the database.
     * @throws DaoException If something fails at database level.
     */
    boolean existEmail(String email) throws DaoException;

    /**
     * Change the password of the given user. The user ID must not be null, otherwise it will throw
     * IllegalArgumentException.
     * @param user The id of user to change the password for.
     * @param newpass The new password
     * @throws IllegalArgumentException If the user ID is null.
     * @throws DaoException If something fails at database level.
     */
    void changePassword(User user, String newpass) throws DaoException;

}
