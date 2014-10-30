package ch.tarsier.tarsier.storage;

import java.sql.Time;
import java.util.Date;

/**
 * Created by McMoudi on 24/10/14.
 */
public class Message {

    private int id, chatID;
    private String content;
    private String author;
    private long dateTime;


    public Message(int id, int chatID, String content, String author, long dateTime) {

        this.id = id;

        this.chatID = chatID;

        this.content = content;

        this.author = author;

        this.dateTime = dateTime;


    }


    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }

    public int getChatID() {
        return chatID;
    }

    public long getDateTime() {
        return dateTime;
    }

}
