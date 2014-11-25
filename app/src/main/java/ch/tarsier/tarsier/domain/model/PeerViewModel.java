package ch.tarsier.tarsier.domain.model;

/**
 * Created by benjamin on 25/11/14.
 */
public class PeerViewModel {

    private String mPeerName;
    private String mPeerStatusMessage;
    private boolean mIsOnline;
    private String mPeerPicturePath;

    public PeerViewModel(Peer peer) {
        mPeerName = peer.getUserName();
        mPeerStatusMessage = peer.getStatusMessage();
        mIsOnline = peer.isOnline();
        mPeerPicturePath = peer.getPicturePath();
    }

    public String getPeerName() {
        return mPeerName;
    }
    public String getPeerStatusMessage() {
        return mPeerStatusMessage;
    }
    public boolean isOnline() {
        return mIsOnline;
    }
    public String getPeerPicturePath() {
        return mPeerPicturePath;
    }

}
