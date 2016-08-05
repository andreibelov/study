package ru.belov.javaee.jsp.dao.impl;

import lombok.Data;
import ru.belov.javaee.jsp.model.Message;

import javax.persistence.Version;
import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by john on 7/22/2016.
 */
@Data
@XmlRootElement(name="root")
@XmlAccessorType(XmlAccessType.FIELD)
class Messages implements Serializable {

    // Constants ----------------------------------------------------------------------------------
    @Version
    private static final long serialVersionUID = 1L;

    // Properties ---------------------------------------------------------------------------------
    @XmlElementWrapper(name="messages")
    @XmlElement(name="message")
    private List<Message> messageList = new CopyOnWriteArrayList<>();
}
