package ru.belov.study.proj5.model.chat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import ru.belov.study.proj5.model.account.User;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by john on 7/22/2016.
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */

@Data
@Entity
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class ChatUser extends User {

    // Constants ----------------------------------------------------------------------------------
    @Version
    private static final long serialVersionUID = 1L;

    // Properties ---------------------------------------------------------------------------------
    private String nickname;
    private String jsessionid;

}