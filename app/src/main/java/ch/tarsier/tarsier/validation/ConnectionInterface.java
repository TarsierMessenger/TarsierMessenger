package ch.tarsier.tarsier.validation;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.util.HashMap;
import ch.tarsier.tarsier.ChatRoom;
import ch.tarsier.tarsier.MyConnection;
import ch.tarsier.tarsier.Server;

/**
 * Created by amirreza on 10/27/14.
 */
public class ConnectionInterface implements Handler.Callback,ChatRoom.MessageTarget,MapChangeListener{

    private static final String TAG = "Connection Interface";
    public static final int MESSAGE_READ = 0x401;
    public static final int MY_HANDLE = 0x402;
    private Server server;

    HashMap<Integer,MyConnection> mConnectionsMap;
    private Handler mHandler;
    private ChatRoom mChatRoom;
    private boolean isServer;

    public void setServer(Server server) {
        this.server = server;
    }



    public void setIsServer(boolean isServer) {
        this.isServer = isServer;
    }

    public boolean isServer() {

        return isServer;
    }

    public ConnectionInterface(ChatRoom chatRoom){
        mHandler = new Handler(this);
        mConnectionsMap = new HashMap<Integer, MyConnection>();
        mChatRoom = chatRoom;
    }

    public void send(Integer id,byte[] message){
        if(mConnectionsMap == null){
            return;
        }
        if (mConnectionsMap.containsKey(id) ) {
            MyConnection conn = mConnectionsMap.get(id);
            conn.write(message);
        }
    }

    public void sendToAll(byte[] message){
        if(mConnectionsMap == null){
            return;
        }
        for(MyConnection conn : mConnectionsMap.values()){
            conn.write(message);
        }
    }

    public HashMap<Integer,MyConnection> getConnectionMaps(){
        return mConnectionsMap;
    }

    @Override
    public boolean handleMessage(Message message) {
        switch (message.what) {
            case MESSAGE_READ:
                byte[] readBuf = (byte[]) message.obj;
                String readMessage = new String(readBuf, 0, message.arg1);
                Log.d(TAG, readMessage);
                (mChatRoom).pushMessage("Peer: " + readMessage);
                break;
            case MY_HANDLE:
                Object obj = message.obj;
                (mChatRoom).setMyConnection((MyConnection) obj);
        }
        return true;
    }

    @Override
    public Handler getHandler() {
        return mHandler;
    }

    @Override
    public void changeEventReceived(MapChangeEvent evt) {
        mConnectionsMap = server.getConnectionMaps();
    }
}

