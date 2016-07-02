package ru.andrw.java.core.model;

import ru.andrw.java.core.model.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

/**
 * Created by john on 7/2/2016.
 */
public class Demo {

    static UserList userList = UserList.getInstance();
    public static void main(String[] args) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(UserList.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.marshal(fromXML("users.xml"), System.out);
    }

    //reads list from file
    private static UserList fromXML(String path) throws JAXBException
    {
        return (UserList) JAXBContext.newInstance(UserList.class)
                .createUnmarshaller().unmarshal(new File(Demo.class.getClassLoader().getResource(path).getFile()));
    }

    private static void toXMLFile(UserList userList) throws JAXBException
    {
        JAXBContext jaxbContext = JAXBContext.newInstance(UserList.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.marshal(userList, new File("userlist.xml"));
    }

}
