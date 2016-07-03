package ru.andrw.java.jsonchat.dao;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;

/**
 * Created by john on 7/3/2016.
 */
public final class DaoFactory {
    private static DAO dao;
    private static String path = "/Chat.xml";

    public static DAO getDao() throws DAOException {
        if (dao!=null) return dao;
        else {

            try {
                dao = (DAO) JAXBContext.newInstance(ChatDAO.class)
                        .createUnmarshaller()
                        .unmarshal(ChatDAO.class.getResourceAsStream(path));
            } catch (JAXBException e) {
                throw  new DAOException();
            }

        } return dao;
    }

    public static void saveDao() throws DAOException {
        JAXBContext jaxbContext = null;
        try {
            jaxbContext = JAXBContext.newInstance(ChatDAO.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();


            File file = new File(ChatDAO.class.getResource(path).getPath());
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(dao, file);

        } catch (JAXBException e) {
            throw  new DAOException();
        }

    }
}
