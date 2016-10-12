package ru.andrw.java.socialnw.dao.impl.list;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.andrw.java.socialnw.dao.DaoException;
import ru.andrw.java.socialnw.dao.UserProfileDao;
import ru.andrw.java.socialnw.model.Profile;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Comparator;
import java.util.stream.IntStream;
import java.util.stream.Collectors;
import java.util.function.Predicate;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

import static java.util.Optional.ofNullable;

/**
 * Created by john on 9/25/2016.
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
class ListUserProfileDao implements UserProfileDao {

    private final Logger logger = LoggerFactory
            .getLogger("ru.andrw.java.socialnw.dao.impl.list.ListUserProfileDao");

    private List<Profile> userProfileList;
    private AtomicLong counter;

    ListUserProfileDao(List<Profile> list, AtomicLong al){
        this.userProfileList = list;
        this.counter = al;
    }

    @Override
    public List<Profile> getUserProfilesSubList(Integer offset, Integer limit)
            throws DaoException {
        Integer result =
                Stream.of(offset,limit)
                        .filter(Objects::nonNull)
                        .filter(e -> e >= 0)
                        .collect(Collectors.toList()).size();
        if (result == 2)
        return userProfileList.stream()
                .skip(offset)
                .limit(limit)
                .collect(Collectors.toList());
        else throw new DaoException("Provided limits is incorrect");
    }

    @Override
    public List<Profile> searchUserProfilesByName(String name) {
        Comparator<Profile> groupByComparator = Comparator.comparing(Profile::getFirstName)
                .thenComparing(Profile::getLastName);
        return userProfileList.stream()
                .filter(e -> e.getFirstName().equalsIgnoreCase(name) || e.getLastName().equalsIgnoreCase(name))
                .sorted(groupByComparator)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Profile> searchUserProfileByEmail(String email) {
        return userProfileList.stream()
        .filter(e -> e.getEmail().equals(email))
        .findFirst();
    }

    @Override
    public Optional<Profile> getUserProfileById(Long id) {
        return userProfileList.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst();
    }

    @Override
    public Profile addUserProfile(Profile profile) throws DaoException {
        if(!userProfileValidator(profile)) throw new DaoException("Provided profile is not valid");
        profile.setId(counter.getAndIncrement());
        userProfileList.add(profile);
        return profile;
    }

    @Override
    public void updateUserProfile(Profile profile) throws DaoException {
        if(!userProfileValidator(profile)) throw new DaoException("UserProfile not valid!");
        else {
            IntStream.range(0, userProfileList.size())
                    .filter(i -> userProfileList.get(i).getId().equals(profile.getId()))
                    .findFirst().ifPresent(i -> userProfileList.set(i,profile));
        }
    }

    private boolean userProfileValidator(Profile profile) {
        return ofNullable(profile).filter(p -> p != null)
                .filter(p -> p.getEmail() != null)
                .filter(p -> p.getFirstName() != null)
                .filter(p -> p.getId() != null)
                .isPresent();
    }
    @Override
    public boolean deleteUserProfile(Long id) {
        Predicate<Profile> profile = p -> p.getId().equals(id);
        return userProfileList.removeIf(profile);
    }
}
