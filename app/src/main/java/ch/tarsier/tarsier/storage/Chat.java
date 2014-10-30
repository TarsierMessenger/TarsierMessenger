package ch.tarsier.tarsier.storage;

/**
 * Created by McMoudi on 24/10/14.
 */
public class Chat {

    private int id;
    private String title;
    private String host;
    private boolean group;


    public Chat(int id, String title, String host, boolean isgroup) {
        this.id = id;
        this.title = title;
        this.host = host;
        group = isgroup;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isGroup() {
        return group;
    }

    public String getHost() {
        return host;
    }
}
