package ru.andrw.java.socialnw.util;

import java.nio.ByteBuffer;
import java.util.UUID;

/**
 * Created by john on 10/22/2016.
 * http://stackoverflow.com/questions/17893609/
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
public class UuidUtils {
    public static UUID asUuid(byte[] bytes) {
        ByteBuffer bb = ByteBuffer.wrap(bytes);
        long firstLong = bb.getLong();
        long secondLong = bb.getLong();
        return new UUID(firstLong, secondLong);
    }

    public static byte[] asBytes(UUID uuid) {
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        return bb.array();
    }
}