package ch.tarsier.tarsier;

import android.os.Handler;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by amirreza on 10/26/14.
 */
public class Client extends Thread{

    private static final String TAG = "Client";
    private Handler handler;
    private MyConnection chat;
    private InetAddress mAddress;
    public Client(Handler handler, InetAddress groupOwnerAddress) {
        this.handler = handler;
        this.mAddress = groupOwnerAddress;
    }
    @Override
    public void run() {
        Socket socket = new Socket();
        try {
            socket.bind(null);
            socket.connect(new InetSocketAddress(mAddress.getHostAddress(),
                    HomeActivity.SERVER_PORT), 5000);
            Log.d(TAG, "Launching the I/O handler");
            chat = new MyConnection(socket, handler);
            new Thread(chat).start();
        } catch (IOException e) {
            e.printStackTrace();
            try {
                socket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return;
        }
    }
    public MyConnection getChat() {
        return chat;
    }

}
