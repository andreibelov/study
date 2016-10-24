package ru.andrw.java.socialnw.model.enums;

/**
 * Created by john on 10/12/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
public enum Gender {

    UNDEFINED("Not selected"),
    MALE("Male"),
    FEMALE("Female"),
    OTHER("Other");

    private final String sex;
    Gender(String gender){
        this.sex = gender;
    }
    public String getSex(){
        return sex;
    }
    public final int getOrdinal() {
        return this.ordinal();
    }
}
