package ch.tarsier.tarsier.domain.model;

/**
 * Created by Marin on 23.11.2014.
 */
public class NearbyChatroomSummary {
    private int mId;
    private String mNearbyChatroomName;
    private String mLastMessage;
    private int mNbPeers;

    public NearbyChatroomSummary(int id, String name, String lastMessage, int nbPeers) {
        mId=id;
        mNearbyChatroomName =name;
        mLastMessage=lastMessage;
        mNbPeers=nbPeers;
    }

    public int getId(){ return mId; }

    public String getChatroomName(){ return mNearbyChatroomName; }

    public String getChatroomLastMessage(){ return mLastMessage; }

    public int getNbPeers(){ return mNbPeers; }
}
