//package ch.tarsier.tarsier.network;
//
//import android.os.Handler;
//import android.util.Log;
//
//import java.io.IOException;
//import java.net.InetAddress;
//import java.net.InetSocketAddress;
//import java.net.Socket;
//
//import ch.tarsier.tarsier.network.client.TarsierMessagingClient;
//
///**
// * Created by amirreza on 10/26/14.
// */
//public class Client extends Thread{
//
//    private static final String TAG = "Client";
//    private Handler handler;
//    private InetAddress mAddress;
//    public Client(Handler handler, InetAddress groupOwnerAddress) {
//        this.handler = handler;
//        this.mAddress = groupOwnerAddress;
//    }
//    @Override
//    public void run() {
//        Socket socket = new Socket();
//        try {
//            socket.bind(null);
//            socket.connect(new InetSocketAddress(mAddress.getHostAddress(),
//                    WiFiDirectDebugActivity.SERVER_PORT), 5000);
//            Log.d(TAG, "Launching the I/O handler");
//            chat = new TarsierMessagingClient.MyConnection(socket, handler, true);
//            new Thread(chat).start();
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            try {
//                socket.close();
//            } catch (IOException e1) {
//                e1.printStackTrace();
//            }
//            return;
//        }
//    }
//}
