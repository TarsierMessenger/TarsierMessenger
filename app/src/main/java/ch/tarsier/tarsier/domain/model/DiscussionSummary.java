package ch.tarsier.tarsier.domain.model;

/**
 * Created by gluthier
 */
public class DiscussionSummary {
    private int mId;
    private String mAvatar;
    private String mNotifications;
    private String mName;
    private String mLastMessage;
    private String mHumanTime;
    private String mNbOnline;
    private TypeConversation mType;

    public DiscussionSummary(int id, String avatar, String notifications, String name,
                             String lastMessage, String humanTime, String nbOnline,
                             TypeConversation type) {
        mId = id;
        mAvatar = avatar;
        mNotifications = notifications;
        mName = name;
        mLastMessage = lastMessage;
        mHumanTime = humanTime;
        mNbOnline = nbOnline;
        mType = type;
    }

    public int getId() {
        return mId;
    }

    public String getAvatar() {
        return mAvatar;
    }

    public String getNotifications() {
        return mNotifications;
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

    public String getNbOnline() {
        return mNbOnline;
    }

    public TypeConversation getType() {
        return mType;
    }

    /**
     * TypeConversation contain the type of the discussion
     */
    public static enum TypeConversation {
        PUBLIC_ROOM, PRIVATE_CHAT
    }

}
