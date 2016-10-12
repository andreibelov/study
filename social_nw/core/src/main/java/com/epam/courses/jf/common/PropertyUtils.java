package com.epam.courses.jf.common;

import java.util.Properties;

/**
 * Created by Vyacheslav Lapin
 * https://github.com/Vyacheslav-Lapin/JavaFundamentals
 * made during java lessons
 */
public interface PropertyUtils {
    static String getAndRemove(Properties properties, String key) {
        return (String) properties.remove(key);
    }
}
