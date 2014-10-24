package ch.tarsier.tarsier.storage;

/**
 * Created by McMoudi on 24/10/14.
 */
public class Chat {

    private int id;
    private String title;
    private String host;

    public Chat(int id, String title, String host) {
        this.id = id;
        this.title = title;
        this.host = host;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getHost() {
        return host;
    }
}
