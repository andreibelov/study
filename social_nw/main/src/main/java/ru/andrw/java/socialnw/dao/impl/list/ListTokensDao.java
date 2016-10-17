package ru.andrw.java.socialnw.dao.impl.list;

import org.apache.tomcat.util.security.ConcurrentMessageDigest;
import org.apache.tomcat.util.security.MD5Encoder;

import java.util.Map;
import java.util.Optional;

import ru.andrw.java.socialnw.dao.TokensDao;
import ru.andrw.java.socialnw.model.auth.User;

import static java.util.Optional.empty;
import static java.util.Optional.of;

/**
 * Created by john on 10/9/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
@SuppressWarnings("WeakerAccess")
class ListTokensDao implements TokensDao {

    private Map<String, Long> tokens;

    ListTokensDao(Map<String, Long> map){
        this.tokens = map;
    }

    @Override
    public void addToken(User user) {
        String hashcode = encode(user.getLogin());
        Long userid = user.getId();
        if (userid != null && hashcode != null)
        tokens.putIfAbsent(hashcode, userid);
    }

    @Override
    public void removeToken(String token) {
        if(token != null) tokens.remove(token);
    }

    @Override
    public Optional<Long> findUserId(String token) {
        return (token != null && tokens.containsKey(token)) ?
                of(tokens.get(token)) : empty();
    }

    private static String encode(String password) {
        return MD5Encoder.encode(ConcurrentMessageDigest.digestMD5(password.getBytes()));
    }
}
