package ru.andrw.java.socialnw.dao;

import com.epam.courses.jf.dao.Dao;

import java.util.List;

import ru.andrw.java.socialnw.model.Profile;
import ru.andrw.java.socialnw.model.enums.FStatus;
import ru.andrw.java.socialnw.model.enums.RowStatus;

/**
 * Created by john on 10/12/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
public interface FriendsDao extends Dao {

    public List<Profile> getFriendsSublist(Long requesterId, Integer offset, Integer limit) throws DaoException;
    public List<Profile> getSentSublist(Long requesterId, Integer offset, Integer limit) throws DaoException;
    public List<Profile> getReceivedSublist(Long requesterId, Integer offset, Integer limit) throws DaoException;
    public List<Profile> getBlockedSublist(Long requesterId, Integer offset, Integer limit) throws DaoException;
    public Integer friendsStatus(Long requesterId, Long requesteeId) throws DaoException;
    public void friendUpdate(Long requesterId, Long requesteeId, RowStatus status) throws DaoException;

}
