package example.control;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

/**
 * Created by john on 8/12/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
@WebServlet(name = "ChatServlet", urlPatterns = {"/chat"})
public class ChatController extends HttpServlet {
}
