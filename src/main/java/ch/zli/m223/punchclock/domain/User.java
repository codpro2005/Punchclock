package ch.zli.m223.punchclock.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private boolean isAdmin;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "creator")
    private List<Entry> entries;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "creator")
    private List<Message> messages;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "creator")
    private List<MessageReceived> messagesReceived;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "creator")
    private List<Note> notes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String email) {
        this.username = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public List<Entry> getEntries() {
        return entries;
    }

    public void setEntries(List<Entry> entries) {
        this.entries = entries;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public List<MessageReceived> getMessagesReceived() {
        return messagesReceived;
    }

    public void setMessagesReceived(List<MessageReceived> messagesReceived) {
        this.messagesReceived = messagesReceived;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

//    public String toJSONString() {
//        String decodedJSON;
//        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
//        try {
//            String encodedJSON = ow.writeValueAsString(this);
//            Object o1 = JSONValue.parse(encodedJSON);
//            JSONObject jsonObj = (JSONObject) o1;
//            decodedJSON =
//                    "\"id\": " + "\"" + (Long) jsonObj.get("id") + "\"" + ", " +
//                    "\"email\": " + "\"" + (String) jsonObj.get("email") + "\"" + ", " +
//                    "\"password\": " + "\"" + (String) jsonObj.get("password") + "\"";
//            ;
//        } catch (JsonProcessingException e) {
//            decodedJSON = "";
//        }
//        return decodedJSON;
//    }
}
