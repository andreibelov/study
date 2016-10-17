package ru.andrw.java.socialnw.model.pkeys;

import java.io.Serializable;

import javax.persistence.Id;

import lombok.Data;

/**
 * Created by john on 10/16/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
@Data
public class RelationPK implements Serializable{

    private Long idRequester;
    private Long idRequestee;

}
