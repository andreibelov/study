package example.model;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by john on 8/12/2016.
 * Represents Message list object model
 */
@Data
public class ChatMessages implements Serializable {

    // Constants ----------------------------------------------------------------------------------

    private static final long serialVersionUID = 1L;

    // Properties ---------------------------------------------------------------------------------

    private List<Message> messageList;

}
