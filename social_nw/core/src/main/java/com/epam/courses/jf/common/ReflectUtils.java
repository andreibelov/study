package com.epam.courses.jf.common;

/**
 * Created by john on 10/10/2016.
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
public interface ReflectUtils {

    static Class<?> loadClass(String classFullName) {
        return loadClass(classFullName, "Class " + classFullName + " wasn`t loaded!");
    }

    static Class<?> loadClass(String classFullName, String message) {
        try {
            return Class.forName(classFullName);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(message, e);
        }
    }


}
