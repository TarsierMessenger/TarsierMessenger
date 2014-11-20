package ch.tarsier.tarsier.domain.model;

/**
 * @author gluthier
 */
public class ChatSummary {
    private long mId;
    private String mAvatar;
    private String mName;
    private String mLastMessage;
    private String mHumanTime;

    public ChatSummary(long id, String avatar, String name, String lastMessage, String humanTime) {
        mId = id;
        mAvatar = avatar;
        mName = name;
        mLastMessage = lastMessage;
        mHumanTime = humanTime;
    }

    public long getId() {
        return mId;
    }

    public String getAvatar() {
        return mAvatar;
    }

    public String getName() {
        return mName;
    }

    public String getLastMessage() {
        return mLastMessage;
    }

    public String getHumanTime() {
        return mHumanTime;
    }
}
