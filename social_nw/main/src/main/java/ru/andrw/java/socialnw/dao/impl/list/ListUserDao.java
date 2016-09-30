package ru.andrw.java.socialnw.dao.impl.list;

import ru.andrw.java.socialnw.dao.DaoException;
import ru.andrw.java.socialnw.dao.UserDao;
import ru.andrw.java.socialnw.model.User;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Optional.ofNullable;

/**
 * Created by john on 9/25/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
class ListUserDao implements UserDao {

    private List<User> users;
    private AtomicLong al;

    ListUserDao(List<User> users, AtomicLong al){
        this.users = users;
        this.al = al;
    }

    @Override
    public List<User> getUsersSubList(final Integer offset, final Integer limit) throws DaoException {

        Integer size = users.size();
        Integer result =
        Stream.of(offset,limit)
                .filter(Objects::nonNull)
                .filter(e -> e >= 0L)
                .filter(e -> e < size)
                .collect(Collectors.summingInt(Integer::intValue));

        if (result != null && result.compareTo(size) <=0)
            return users.stream()
                    .skip(offset)
                    .limit(limit)
                    .collect(Collectors.toList());
        else throw new DaoException("Provided limits is out of bound");
    }

    @Override
    public Optional<User> getUserById(Long id) throws DaoException {
        return users.stream().filter(user -> id.equals(user.getId()))
                .findAny();
    }

    @Override
    public Optional<User> findUser(String email, String pass) throws DaoException {
        return users.stream().filter(u -> u.getEmail().equals(email))
                .findAny().filter(u -> u.getPassword().equals(pass));
    }

    @Override
    public void addUser(User user) throws DaoException {
        if(!userValidator(user)) throw new DaoException();
        else users.add(user.setId(al.getAndIncrement()));
    }

    private boolean userValidator(User user){
        return ofNullable(user).filter(u -> u != null)
                .filter(u -> u.getLogin() != null)
                .filter(u -> u.getPassword() != null)
                .filter(u -> u.getEmail() != null)
                .isPresent();
    }

    @Override
    public void deleteUser(Long userId) throws DaoException {
        IntStream.range(0, users.size())
                .filter(i -> users.get(i).getId().equals(userId))
                .findAny().ifPresent(i -> users.remove(i));
    }

    @Override
    public void changePassword(Long userId, String pass) throws DaoException {
        IntStream.range(0, users.size())
                .filter(i -> users.get(i).getId().equals(userId))
                .findFirst().ifPresent(i -> users.get(i).setPassword(pass));
    }

    @Override
    public void updateUser(User user) throws DaoException {
        if(!userValidator(user)) throw new DaoException();
        else {
            IntStream.range(0, users.size())
                    .filter(i -> users.get(i).getId().equals(user.getId()))
                    .findFirst().ifPresent(i -> users.set(i,user));
        }
    }

}
