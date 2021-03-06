package ru.andrw.java.chat.dao.factory;

import ru.andrw.java.chat.dao.exeptions.DAOException;
import ru.andrw.java.chat.dao.ifaces.UserDAO;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;

/**
 * Created by john on 7/2/2016.
 *
 */
public final class DAOFactory {
    private static UserDAO dao;
    private static String path = "/Chat.xml";

    public static UserDAO getDao() throws DAOException {
        if (dao!=null) return dao;
        else {

            try {
                dao = (UserDAO) JAXBContext.newInstance(UserDAO.class)
                        .createUnmarshaller()
                        .unmarshal(UserDAO.class.getResourceAsStream(path));
            } catch (JAXBException e) {
                throw  new DAOException();
            }

        } return dao;
    }

    public static void saveDao() throws DAOException {
        JAXBContext jaxbContext = null;
        try {
            jaxbContext = JAXBContext.newInstance(UserDAO.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();


            File file = new File(UserDAO.class.getResource(path).getPath());
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(dao, file);

        } catch (JAXBException e) {
            throw  new DAOException();
        }

    }
}
