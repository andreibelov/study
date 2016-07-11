package ru.andrw.java.jdbc.test.control;

import ru.andrw.java.jdbc.test.model.UserList;


import javax.persistence.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.util.Date;


/**
 * Created by john on 7/5/2016.
 */
public class Main {

    public static void main(String[] args) throws Exception {


        JAXBContext jaxbContext = null;
        try {
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("PU");
            EntityManager em = entityManagerFactory.createEntityManager();
            EntityTransaction et = em.getTransaction();

            jaxbContext = JAXBContext.newInstance(UserList.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            UserList userList = fromXML("/META-INF/users.xml");

            System.out.println(UserList.users);
            //Long id, Date birthday, String email, String login,
            // String phone, String password, String firstname,
            // String lastname
            String[] map = new String[]
                    {"email", "login", "+79112935537", "password", "firstname", "lastname"};
            UserList.addUser(map);
            System.out.println(UserList.users);
            et.begin();
            em.persist(UserList.getUser(1L));
            em.persist(UserList.getUser(2L));
            em.persist(UserList.getUser(3L));
            et.commit();

        } catch (JAXBException e) {
            e.printStackTrace();
        }
        //Persistence.generateSchema("user", null);
    }

    private static UserList fromXML(String path) throws JAXBException {
        //reads list from file
        return (UserList) JAXBContext.newInstance(UserList.class)
                .createUnmarshaller().unmarshal(UserList.class.getResourceAsStream(path));
    }
}
