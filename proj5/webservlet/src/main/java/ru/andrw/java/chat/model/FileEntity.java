package ru.andrw.java.chat.model;

import java.io.Serializable;

import lombok.Data;

/**
 * Created by john on 10/5/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
@Data
public class FileEntity implements Serializable {


    private Long id;
    private String name;
    private Long contentLength;
    private Long lastModified;

}
