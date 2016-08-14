package ru.belov.study.proj5.servlet;

import com.google.gson.Gson;
import ru.belov.study.proj5.dao.DaoException;
import ru.belov.study.proj5.dao.impl.ChatDaoJdbc;
import ru.belov.study.proj5.model.account.User;
import ru.belov.study.proj5.model.chat.ChatMessage;
import ru.belov.study.proj5.model.chat.JsonText;
import ru.belov.study.proj5.service.ChatQueue;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Deque;
import java.util.UUID;

/**
 * Created by john on 7/22/2016.
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
@WebServlet(name = "Chat", urlPatterns = {"/chat"})
public class Chat extends javax.servlet.http.HttpServlet {
    private Gson gson;
    private ChatQueue queue;
    private ChatDaoJdbc chatdao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext sc = config.getServletContext();
        gson =      (Gson) sc.getAttribute("gson");
        queue =     (ChatQueue) sc.getAttribute("msgQueue");
        chatdao =   (ChatDaoJdbc) sc.getAttribute("chatdao");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();

        User user = (User) session.getAttribute("chat_user");
        Deque<ChatMessage> messageDeque = (Deque<ChatMessage>) session.getAttribute("user_deque");
        StringBuilder sb = new StringBuilder();
        String line = null;
        try (BufferedReader reader = req.getReader()) {
            while ((line = reader.readLine()) != null)
                sb.append(line);
        } catch (Exception e) { /*report an error*/ }
        JsonText jsonText = gson.fromJson(sb.toString(), JsonText.class);

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("PU");
        EntityManager em = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = em.getTransaction();


        ChatMessage message = new ChatMessage().setFrom(user)
                .setTimestamp(new Date())
                .setText(jsonText.getText())
                .setUuid(UUID.randomUUID());

        try {
            transaction.begin();
            em.persist(message);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
        queue.addNewMsg(message);
    }
}
