package ch.tarsier.tarsier;

/**
 * Created by gluthier on 27.10.2014.
 */
public class DiscussionSummary {
    protected String mAvatarImage;
    protected String mNotifications;
    protected String mName;
    protected String mLastMessage;
    protected String mHumanTime;
    protected String mNbOnline;
    protected TypeConversation mType;

    public DiscussionSummary(String avatarImage, String notifications,
                             String name, String lastMessage,
                             String humanTime, String nbOnline,
                             TypeConversation type) {
        this.mAvatarImage = avatarImage;
        this.mNotifications = notifications;
        this.mName = name;
        this.mLastMessage = lastMessage;
        this.mHumanTime = humanTime;
        this.mNbOnline = nbOnline;
        this.mType = type;
    }

    public static enum TypeConversation {
        PUBLIC_ROOM, PRIVATE_CHAT
    }

}
