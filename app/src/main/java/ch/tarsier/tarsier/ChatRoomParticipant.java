package ch.tarsier.tarsier;

/**
 * @author Romain Ruetschi
 */
public class ChatRoomParticipant {

    private String mName;
    private String mStatusMessage;

    public ChatRoomParticipant(String name, String statusMessage) {
        this.mName = name;
        this.mStatusMessage = statusMessage;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getStatusMessage() {
        return mStatusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.mStatusMessage = statusMessage;
    }
}
