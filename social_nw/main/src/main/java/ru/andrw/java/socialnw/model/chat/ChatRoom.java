package ru.andrw.java.socialnw.model.chat;

import org.eclipse.persistence.annotations.Index;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import ru.andrw.java.socialnw.model.auth.User;

/**
 * Created by john on 9/30/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
@Data
@Entity
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper=true, includeFieldNames=true)
public class ChatRoom extends Conversation implements Serializable {

    // Constants ------------------------------------------------------------------

    private static final long serialVersionUID = 1L;

    // Constructors ---------------------------------------------------------------

    public ChatRoom(String description){
        this.description = description;
    }
    public ChatRoom(){}

    // Properties -----------------------------------------------------------------

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private Long moderator;
    private String description;
    @OneToMany
    private List<User> members;

    public void method(){
        members.stream().parallel().map(User::getId);
        IntStream.range(0,100).parallel().mapToObj(i->members.get(i)).collect(Collectors.toList());
    }
}
