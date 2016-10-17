package ru.andrw.java.socialnw.dao.impl.h2;

import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;

import org.h2.fulltext.FullText;
import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.net.URI;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

import ru.andrw.java.socialnw.dao.DaoException;
import ru.andrw.java.socialnw.dao.IMDao;
import ru.andrw.java.socialnw.model.Attach;
import ru.andrw.java.socialnw.model.auth.User;
import ru.andrw.java.socialnw.model.chat.ChatRoom;
import ru.andrw.java.socialnw.model.chat.Conversation;
import ru.andrw.java.socialnw.model.chat.Dialogue;
import ru.andrw.java.socialnw.model.chat.IMessage;
import ru.andrw.java.socialnw.model.enums.AttType;

import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.math.NumberUtils.max;
import static org.apache.commons.lang3.math.NumberUtils.min;

/**
 * Created by john on 10/15/2016.
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
@SuppressWarnings("FieldCanBeLocal")
class H2IMDao implements IMDao {

    private final Supplier<Connection> supplier;
    private final String SPLITERATOR = ".";
    private final String SCHEMA_NAME = "PUBLIC";
    private final String USER_TBL = "USER";
    private final String USER_TNAME = SCHEMA_NAME+SPLITERATOR+ USER_TBL;
    private final String CHROOM_TBL = "CHATROOM";
    private final String CHROOM_TNAME = SCHEMA_NAME+SPLITERATOR+CHROOM_TBL;
    private final String DIAL_TBL = "DIALOGUE";
    private final String DIAL_TNAME = SCHEMA_NAME+SPLITERATOR+DIAL_TBL;
    private final String IM_TBL = "IMESSAGE";
    private final String IM_TNAME = SCHEMA_NAME+SPLITERATOR+IM_TBL;
    private final String IM_ATTACH_TBL = "IMESSAGE_ATTACHMENTS";
    private final String IM_ATTACH_TNAME = SCHEMA_NAME+SPLITERATOR+IM_ATTACH_TBL;
    private final String CHROOM_USER_TBL = "CHATROOM_USER";
    private final String CHROOM_USER_TNAME = SCHEMA_NAME+SPLITERATOR+CHROOM_USER_TBL;
    @Language("H2")
    private final String SELECT_CHAT_USERS = "SELECT U.ID, U.ACCESSLEVEL, U.EMAIL, U.LOGIN FROM "+USER_TNAME+" U " +
            "  JOIN CHATROOM_USER M ON U.ID = M.MEMBERS_ID " +
            "WHERE M.CHATROOM_ID =?;";
    private final String INSERT_CHR = "INSERT INTO  "+CHROOM_TNAME+" (DESCRIPTION, MODERATOR, UUID) VALUES (?,?,?);";
    private final String INSERT_CHUSR = "INSERT INTO "+CHROOM_USER_TNAME+" (CHATROOM_ID, MEMBERS_ID) VALUES (?,?);";
    @Language("H2")
    private final String DELETE_CHUSR = "DELETE FROM "+CHROOM_USER_TNAME+" WHERE ((CHATROOM_ID = ?) AND (MEMBERS_ID =?));";
    private final String INSERT_MSG = "INSERT INTO "+IM_TNAME+" (UUID, CONVOID, CONVOUUID, CONTENT, CREATED, SENDER, ISREAD) VALUES (?,?,?,?,?,?,?);";
    private final String INSERT_MSG_ATTACH = "INSERT INTO "+IM_ATTACH_TNAME+
            " (TYPE, URI, UUID, IMESSAGE_ID ) VALUES (?,?,?,?);";
    private final String SELECT_ATTACH = "SELECT TYPE, URI, UUID FROM "+IM_ATTACH_TNAME+
            " WHERE IMESSAGE_ID = ?";
    private final String IMS_FROM_DATE =
            "SELECT ID, CONVOID, CONVOUUID, CONTENT, CREATED, ISREAD, SENDER, UUID FROM " + IM_TNAME +
                    " WHERE ((CONVOUUID = ?) AND (CREATED > ?)) ORDER BY ID DESC LIMIT ? OFFSET ?;";
    private final String SELECT_DIAL_BY_ID =
            "SELECT ID,PERSON1,PERSON2, STARTED, LASTUPDATE, UUID FROM "+DIAL_TNAME+" WHERE (ID = ?);";
    @Language("H2")
    private final String SELECT_CHROOM_BY_ID =
            "SELECT ID, STARTED, DESCRIPTION, MODERATOR, LASTUPDATE, UUID FROM "+CHROOM_TNAME+" WHERE (ID=?);";
    @Language("H2")
    private final String SELECT_CONVOS = "SELECT R.ID, R.STARTED, R.LASTUPDATE, R.UUID," +
            "  IM.ID, IM.CONVOID, IM.CONVOUUID, IM.CONTENT, IM.CREATED, IM.ISREAD, IM.SENDER, IM.UUID " +
            "FROM (" +
            "  SELECT ID, STARTED, LASTUPDATE, UUID" +
            "  FROM PUBLIC.CHATROOM" +
            "    LEFT JOIN CHATROOM_USER ON CHATROOM.ID = CHATROOM_USER.CHATROOM_ID" +
            "  WHERE MEMBERS_ID = ?" +
            "  UNION ALL" +
            "  SELECT ID, STARTED, LASTUPDATE, UUID" +
            "  FROM PUBLIC.DIALOGUE" +
            "  WHERE PERSON1 = ?" +
            "  UNION ALL" +
            "  SELECT ID,STARTED AS CREATED, LASTUPDATE, UUID" +
            "  FROM PUBLIC.DIALOGUE" +
            "  WHERE PERSON2 = ?" +
            ") R " +
            "JOIN IMESSAGE IM ON CREATED = LASTUPDATE AND CONVOUUID = R.UUID " +
            "ORDER BY LASTUPDATE DESC LIMIT ? OFFSET ?;";
    @Language("H2")
    private final String NEW_DIAL =
            "INSERT INTO "+DIAL_TNAME+" (ID, PERSON1, PERSON2, STARTED, LASTUPDATE, UUID) VALUES (?,?,?,?,?,?);";

    private static final EthernetAddress addr = EthernetAddress.fromInterface();
    private static final TimeBasedGenerator uuidIM = Generators.timeBasedGenerator(addr);
    private static final TimeBasedGenerator uuidAttch = Generators.timeBasedGenerator(addr);
    private static final TimeBasedGenerator uuidConvo = Generators.timeBasedGenerator(addr);

    H2IMDao(Supplier<Connection> supplier){
        this.supplier = supplier;
    }

    @Override
    public ChatRoom newChatRoom(ChatRoom chatRoom) throws DaoException{
        if(!chatRoomValidator(chatRoom)) throw new DaoException("Chat room members list doesn't contains moderatorId!");
        else try(Connection con = getConnection()){
            boolean oldAutoCommit=con.getAutoCommit();
            con.setAutoCommit(false);//auto commit to false
            try(
                 PreparedStatement ps1 = con.prepareStatement(INSERT_CHR, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement ps2 = con.prepareStatement(INSERT_CHUSR)
            ){
                prepareRoom(chatRoom, ps1);
                postInspection(ps1, "Creating chatRoom failed, no rows affected.");
                try (ResultSet generatedKeys = ps1.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        chatRoom.setId(generatedKeys.getLong(1));
                    }
                    else {
                        throw new SQLException("Creating chatRoom failed, no ID obtained.");
                    }
                }
                for (User user : chatRoom.getMembers()) {
                    prepareChUser(user.getId(),chatRoom.getId(), ps2);
                    ps2.executeUpdate();
                }
                con.commit();
            } catch (SQLException e) {
                try {
                    con.rollback();
                    throw new DaoException(e);
                } catch(SQLException excep) {
                    throw new DaoException(excep);
                }
            } finally {
                con.setAutoCommit(oldAutoCommit);//reset auto commit
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return chatRoom;
    }

    @Override
    public Map<Conversation, IMessage> getConversationList(Long userid, Integer offset, Integer limit){
        Map<Conversation, IMessage> convos = new HashMap<>();
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_CONVOS)
        ) {
            ps.setLong(1,userid);
            ps.setInt(2,limit);
            ps.setInt(3,offset);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Conversation convo = mapConvo(rs);
                IMessage message = mapMessage(rs,4)
                        .setAttachments(getAttachments(rs.getLong(5)));
                convos.put(convo,message);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return convos;
    }

    @Override
    public List<IMessage> getIMsSinceDate(UUID convoUUID, Date from,
                                         Integer offset, Integer limit) throws DaoException{
        List<IMessage> messages = new ArrayList<>();
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(IMS_FROM_DATE)
        ) {
            ps.setObject(1,convoUUID);
            ps.setTimestamp(2,new Timestamp(from.getTime()));
            ps.setInt(3, limit);
            ps.setInt(4, offset);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                messages.add(mapMessage(rs,0)
                        .setAttachments(getAttachments(rs.getLong(1))));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return messages;
    }

    @Override
    public IMessage sendMessageToUser(IMessage message, Long senderId,
                                      Long receiverId) throws DaoException{
        Dialogue dialogue = mapDialogue(senderId,receiverId);
        message.setConvoId(dialogue.getId()).setConvoUuid(dialogue.getUuid());
        return  addNewIM(message);
    }

    //TODO Implemet method
    public List<IMessage> fullTextSearch(String text, Long requesterId) throws DaoException{

//        try(Connection con = getConnection();
//            Statement stat = con.createStatement();
//        ) {
//            stat.execute("CREATE ALIAS IF NOT EXISTS FT_INIT FOR \"org.h2.fulltext.FullText.init\"");
//            stat.execute("CALL FT_INIT()");
//            FullText.setIgnoreList(con, "to,this");
//            FullText.setWhitespaceChars(con, " ,.-");
//            stat.execute("CALL FT_CREATE_INDEX('PUBLIC', 'TABLE', 'COLUMN1,COLUMN2')");
//            ResultSet rs;
//            PreparedStatement ps = con.prepareStatement("SELECT * FROM FT_SEARCH(?, 0, 0);");
//            ps.setString(1, text);
//            rs = ps.executeQuery();
//
//        } catch (SQLException e) {
//            throw new DaoException(e);
//        }

        return null;
    }

    @Override
    public void inviteUser(final Long userId, final Long inviterId, final Long chatRoomId) throws DaoException {
        Optional<ChatRoom> byId = getChatRoomById(chatRoomId);
        if(!byId.isPresent()) throw new DaoException("Chatroom not found");
        else if(!byId.get().getModerator().equals(inviterId) ||
                !byId.get().getMembers()
                        .stream().filter(u -> u.getId().equals(inviterId)).findAny().isPresent())
            throw new DaoException("You can not do this");
        else try(Connection con = getConnection();
                 PreparedStatement ps = con.prepareStatement(INSERT_CHUSR)){
                prepareChUser(userId,chatRoomId, ps);
                postInspection(ps,"Invitation failed, now rows affected");
        } catch (SQLException e) {throw new DaoException(e);}
    }

    @Override
    public void kickUser(Long userId, Long moderatorId, Long chatRoomId) throws DaoException {
        Optional<ChatRoom> byId = getChatRoomById(chatRoomId);
        if(!byId.isPresent()) throw new DaoException("Chatroom not found");
        else if(!userId.equals(moderatorId)) throw new DaoException("You can not do this");
        else try(Connection con = getConnection();
                 PreparedStatement ps = con.prepareStatement(DELETE_CHUSR)){
                prepareChUser(userId,chatRoomId, ps);
                postInspection(ps,"Kicking user failed, now rows affected");
            } catch (SQLException e) {throw new DaoException(e);}
    }

    private Optional<ChatRoom> getChatRoomById(Long chatRoomId) {
        ChatRoom byId = null;
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_CHROOM_BY_ID)
        ) {
            ps.setLong(1, chatRoomId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                byId = mapChatRoom(rs);
            }

        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return ofNullable(byId);
    }

    @Override
    public IMessage addNewIM(IMessage message) throws DaoException{
        if(!messageValidator(message)) throw new DaoException("Message is not valid!");
        else try(Connection con = getConnection()){
            boolean oldAutoCommit=con.getAutoCommit();
            con.setAutoCommit(false);//auto commit to false
            try(PreparedStatement ps1 = con.prepareStatement(INSERT_MSG, Statement.RETURN_GENERATED_KEYS);
                PreparedStatement ps2 = con.prepareStatement(INSERT_MSG_ATTACH)
            ){
            prepareMessage(message, ps1);
            postInspection(ps1, "Creating message failed, no rows affected.");

            try (ResultSet generatedKeys = ps1.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    message.setId(generatedKeys.getLong(1));
                }
                else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }

            for (Attach attach: message.getAttachments()){
                prepareAttachmnt(attach,message.getId(),ps2);
                ps2.executeUpdate();
            }
            con.commit();
            } catch (SQLException e) {
                try {
                    con.rollback();
                    throw new DaoException(e);
                } catch(SQLException excep) {
                    throw new DaoException(excep);
                }
            } finally {
                con.setAutoCommit(oldAutoCommit);//reset auto commit
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return message;
    }

    @Override
    public void deleteMessage(IMessage message, Long userId) throws DaoException {
        // TODO
    }

    @Override
    public Connection getConnection() {
        return supplier.get();
    }

    // Private methods -------------------------------------------------------------------

    private void prepareMessage(IMessage message, PreparedStatement ps1) throws SQLException {
        ps1.setObject(1,uuidIM.generate());
        ps1.setLong(2, message.getConvoId());
        ps1.setObject(3, message.getConvoUuid());
        ps1.setString(4,message.getContent());
        ps1.setObject(4,new Timestamp(message.getCreated().getTime()));
    }

    private void prepareAttachmnt(Attach attach, Long id,
                                  PreparedStatement ps2) throws SQLException {

        ps2.setInt(1,attach.getType().ordinal());
        ps2.setObject(2,attach.getUri());
        ps2.setObject(3,uuidAttch);
        ps2.setLong(4,id);
    }

    private boolean messageValidator(IMessage message) {
        return message != null &&
                message.getSender() != null &&
                message.getConvoUuid() != null &&
                message.getCreated() != null;
    }

    private void prepareChUser(Long userId, Long chRoomId,
                               PreparedStatement ps) throws SQLException {
        ps.setLong(1, chRoomId);
        ps.setLong(2, userId);
    }

    private void prepareRoom(ChatRoom chatRoom,
                             PreparedStatement ps1) throws SQLException {
        ps1.setString(1,chatRoom.getDescription());
        ps1.setLong(2,chatRoom.getModerator());
        ps1.setObject(3, uuidConvo.generate());
        ps1.setTimestamp(4,new Timestamp(chatRoom.getStarted().getTime()));
        ps1.setTimestamp(5,new Timestamp(chatRoom.getLastUpdate().getTime()));
    }

    private boolean chatRoomValidator(ChatRoom chatRoom) {
        return (chatRoom != null) && chatRoom.getMembers().size()>1 &&
                chatRoom.getMembers()
                        .stream()
                        .filter(u -> u.getId().equals(chatRoom.getModerator()))
                .findAny().isPresent();
    }

    private List<Attach> getAttachments(Long imId) throws DaoException {
        List<Attach> attachments = new ArrayList<>();
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_ATTACH)
        ) {
            ps.setLong(1,imId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                attachments.add(mapAttachment(rs));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return attachments;
    }

    private Attach mapAttachment(ResultSet rs) throws SQLException {
        return (new Attach())
                .setType(AttType.values()[rs.getInt(1)])
                .setUri(rs.getObject(2, URI.class))
                .setUuid(rs.getObject(1,UUID.class));
    }

    private Dialogue createDialogue(final Long id, final Long senderId, final Long receiverId) {
        Dialogue dialogue = new Dialogue();
        dialogue.setId(id)
                .setPerson1(senderId)
                .setPerson2(receiverId);
        dialogue.setStarted(new Date());
        dialogue.setLastUpdate(dialogue.getStarted())
                .setUuid(uuidConvo.generate());

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(NEW_DIAL)
        ){
            prepareDialogue(id, senderId, receiverId, dialogue, ps);
            postInspection(ps, "Creating dialogue failed, no rows affected.");
        } catch (SQLException e) {throw new DaoException(e);}

        return dialogue;
    }

    private void prepareDialogue(Long id, Long senderId, Long receiverId, Dialogue dialogue, PreparedStatement ps) throws SQLException {
        ps.setLong(1,id);
        ps.setLong(2,senderId);
        ps.setLong(3,receiverId);
        ps.setTimestamp(4, new Timestamp(dialogue.getStarted().getTime()));
        ps.setTimestamp(5, new Timestamp(dialogue.getStarted().getTime()));
        ps.setObject(6,dialogue.getUuid());
    }

    private Optional<Dialogue> getDialogueById(Long id) throws DaoException{
        Dialogue byId = null;
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_DIAL_BY_ID)
        ) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                byId = mapDialogue(rs);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        return ofNullable(byId);
    }

    private Dialogue mapDialogue(Long senderId, Long receiverId){
        Long id = cantorPairing(max(senderId,receiverId),min(senderId,receiverId));
        Optional<Dialogue> byId = getDialogueById(id);
        return byId.isPresent()? byId.get(): createDialogue(id,senderId, receiverId);
    }

    @NotNull
    private Dialogue mapDialogue(ResultSet rs) throws SQLException {
        Dialogue byId;
        byId = (new Dialogue())
                .setId(rs.getLong(1))
                .setPerson1(rs.getLong(2))
                .setPerson2(rs.getLong(3));
        byId.setStarted(rs.getTimestamp(4))
                .setLastUpdate(rs.getTimestamp(5))
                .setUuid(rs.getObject(6, UUID.class));
        return byId;
    }

    @NotNull
    private ChatRoom mapChatRoom(ResultSet rs) throws SQLException {
        ChatRoom byId;
        byId = (new ChatRoom())
                .setId(rs.getLong(1))
                .setDescription(rs.getString(3))
                .setModerator(rs.getLong(4));
        byId.setLastUpdate(rs.getTimestamp(5))
                .setStarted(rs.getTimestamp(2))
                .setUuid(rs.getObject(6,UUID.class));
        List<User> members = new ArrayList<>();
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_CHAT_USERS)
        ) {
            ps.setLong(1,byId.getId());
            ResultSet rs1 = ps.executeQuery();
            while (rs1.next()) {
                members.add(mapUser(rs1));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
        byId.setMembers(members);
        return byId;
    }

    @Contract(pure = true)
    private Long cantorPairing(Long x, Long y) {
        return ((x+y)*(x+y+1L)/2L+y);
    }

    private User mapUser(ResultSet rs) throws SQLException {
        return (new User())
                .setId(rs.getLong(1))
                .setAccessLevel(rs.getInt(2))
                .setEmail(rs.getString(3))
                .setLogin(rs.getString(4));
    }

    private void postInspection(PreparedStatement ps1, String cause) throws SQLException {
        int affectedRows = ps1.executeUpdate();
        if (affectedRows == 0) {
            throw new SQLException(cause);
        }
    }

    private IMessage mapMessage(ResultSet rs, int o) throws SQLException {
        return (new IMessage())
                .setId(rs.getLong(++o))
                .setConvoId(rs.getLong(++o))
                .setConvoUuid(rs.getObject(++o,UUID.class))
                .setContent(rs.getString(++o))
                .setCreated(rs.getTimestamp(++o))
                .setRead(rs.getBoolean(++o))
                .setSender(rs.getLong(++o))
                .setUuid(rs.getObject(++o,UUID.class));
    }

    private Conversation mapConvo(ResultSet rs) throws SQLException {
        Conversation convo = new Conversation();
        convo.setId(rs.getLong(1))
                .setStarted(rs.getTimestamp(2))
                .setLastUpdate(rs.getTimestamp(3))
                .setUuid(rs.getObject(4, UUID.class));
        return convo;
    }

}
