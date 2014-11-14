package ch.tarsier.tarsier.network.old;

import android.os.Handler;
import android.util.Log;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @author amirreza
 */
public class Client extends Thread{

    private static final String TAG = "Client";
    private Handler mHandler;
    private MyConnection mChat;
    private InetAddress mAddress;
    public Client(Handler handler, InetAddress groupOwnerAddress) {
        this.mHandler = handler;
        this.mAddress = groupOwnerAddress;
    }
    @Override
    public void run() {
        Socket socket = new Socket();
        try {
            socket.bind(null);
            socket.connect(new InetSocketAddress(mAddress.getHostAddress(),
                    WiFiDirectDebugActivity.SERVER_PORT), 5000);
            Log.d(TAG, "Launching the I/O mHandler");
            mChat = new MyConnection(socket, mHandler);
            new Thread(mChat).start();
        } catch (IOException e) {
            e.printStackTrace();
            try {
                socket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
    public MyConnection getChat() {
        return mChat;
    }

}
