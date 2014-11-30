package ch.tarsier.tarsier.domain.model;

/**
 * @author gluthier
 */
public class ChatSummary {
    private long mId;
    private String mAvatarSrc;
    private String mName;
    private String mLastMessage;
    private String mHumanTime;

    public ChatSummary(Chat chat) {
        mId = chat.getId();
        mAvatarSrc = chat.getAvatarRessourceId();
        Peer host = chat.getHost();
        mName = host.getUserName();


        mId = id;
        mAvatarSrc = avatar;
        mName = name;
        mLastMessage = lastMessage;
        mHumanTime = humanTime;
    }

    public long getId() {
        return mId;
    }

    public String getAvatar() {
        return mAvatarSrc;
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
