package ru.andrw.java.core.model;

import com.sun.org.apache.bcel.internal.generic.INSTANCEOF;
import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.*;
import java.io.File;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.util.Optional.ofNullable;

/**
 * Created by john on 7/2/2016.
 *
 */
public class Demo {

    public static void main(String[] args)
    {
        JAXBContext jaxbContext = null;
        try {
            jaxbContext = JAXBContext.newInstance(UserList.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            UserList userList = fromXML("/users.xml");

            List<User> users = userList.getUsers();

            User user = users.get(0).setBirthday(new Date(618328800000L));
            users.set(0, user);


            userList.setUsers(users);

            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(userList, System.out);
            toXMLFile(userList,"/userlist.xml" );
        } catch (JAXBException e) {
            System.out.println(e.toString());
        }

    }

    private static void toXMLFile(UserList userList,String path) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(UserList.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        File file = new File(UserList.class.getResource(path).getPath());
        jaxbMarshaller.marshal(userList, file);
    }

    private static UserList fromXML(String path) throws JAXBException {
        //reads list from file
        return (UserList) JAXBContext.newInstance(UserList.class)
                .createUnmarshaller().unmarshal(UserList.class.getResourceAsStream(path));
    }

}
@Getter
@Setter
@XmlRootElement(name="root")
@XmlAccessorType(XmlAccessType.FIELD)
class UserList {
    @XmlElementWrapper(name="users")
    @XmlElement(name="user")
    private List<User> users;
}