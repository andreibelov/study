package ru.andrw.java.socialnw.dao.impl.list;

import ru.andrw.java.socialnw.dao.DaoException;
import ru.andrw.java.socialnw.dao.UserProfileDao;
import ru.andrw.java.socialnw.model.UserProfile;

import java.util.List;
import java.util.Optional;
import java.util.Comparator;
import java.util.stream.IntStream;
import java.util.stream.Collectors;
import java.util.function.Predicate;
import java.util.concurrent.atomic.AtomicLong;

import static java.util.Optional.ofNullable;

/**
 * Created by john on 9/25/2016.
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
class ListUserProfileDao implements UserProfileDao {

    private List<UserProfile> userProfileList;
    private AtomicLong counter;

    ListUserProfileDao(List<UserProfile> list, AtomicLong al){
        this.userProfileList = list;
        this.counter = al;
    }

    @Override
    public List<UserProfile> getUserProfilesSubList(Long skipFirst, Long limitMax) throws DaoException {
        if (skipFirst != null && limitMax != null)
        return userProfileList.stream()
                .skip(skipFirst)
                .limit(limitMax)
                .collect(Collectors.toList());
        else throw new DaoException("Provided limits is out of bound");
    }

    @Override
    public List<UserProfile> searchUserProfilesByName(String name) {
        Comparator<UserProfile> groupByComparator = Comparator.comparing(UserProfile::getName)
                .thenComparing(UserProfile::getLastName);
        return userProfileList.stream()
                .filter(e -> e.getName().equalsIgnoreCase(name) || e.getLastName().equalsIgnoreCase(name))
                .sorted(groupByComparator)
                .collect(Collectors.toList());
    }

    @Override
    public UserProfile searchUserProfileByEmail(String email) throws DaoException {
        Optional<UserProfile> match
                = userProfileList.stream()
                .filter(e -> e.getEmail().equals(email))
                .findFirst();
        if (match.isPresent()) {
            return match.get();
        } else {
            throw new DaoException("The UserProfile with " + email + " not found");
        }
    }

    @Override
    public UserProfile getUserProfileById(Long id) throws DaoException {
        Optional<UserProfile> match
                = userProfileList.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst();
        if (match.isPresent()) {
            return match.get();
        } else {
            throw new DaoException("The UserProfile id " + id + " not found");
        }
    }

    @Override
    public Long addUserProfile(UserProfile profile) throws DaoException {
        if(!userProfileValidator(profile)) throw new DaoException("Provided profile is not valid");
        profile.setId(counter.getAndIncrement());
        userProfileList.add(profile);
        return profile.getId();
    }

    @Override
    public boolean updateUserProfile(UserProfile profile){
        if(!userProfileValidator(profile)) return false;
        else {
            IntStream.range(0, userProfileList.size())
                    .filter(i -> userProfileList.get(i).getId().equals(profile.getId()))
                    .findFirst().ifPresent(i -> userProfileList.set(i,profile));
            return true;
        }
    }

    private boolean userProfileValidator(UserProfile profile) {
        return ofNullable(profile).filter(p -> p != null)
                .filter(p -> p.getEmail() != null)
                .filter(p -> p.getName() != null)
                .filter(p -> p.getUserid() != null)
                .filter(p -> p.getBirthDate() != null)
                .isPresent();
    }
    @Override
    public boolean deleteUserProfile(Long id) {
        Predicate<UserProfile> profile = p -> p.getId().equals(id);
        return userProfileList.removeIf(profile);
    }
}
