package ch.tarsier.tarsier.domain.model;

/**
 * Created by Marin on 23.11.2014.
 */
public class ChatRoomSummary{
    private int mId;
    private String mChatRoomName;
    private String mLastMessage;
    private int mNbPeers;

    public ChatRoomSummary(int id, String name, String lastMessage, int nbPeers ){
        mId=id;
        mChatRoomName=name;
        mLastMessage=lastMessage;
        mNbPeers=nbPeers;
    }

    public int getId(){ return mId; }

    public String getChatRoomName(){ return mChatRoomName; }

    public String getChatRoomLastMessage(){ return mLastMessage; }

    public int getNbPeers(){ return mNbPeers; }
}
