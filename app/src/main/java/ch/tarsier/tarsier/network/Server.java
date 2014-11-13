//package ch.tarsier.tarsier.network;
//
//import android.os.Handler;
//import android.util.Log;
//
//import java.io.IOException;
//import java.net.ServerSocket;
//import java.util.HashMap;
//import java.util.Random;
//import java.util.concurrent.LinkedBlockingQueue;
//import java.util.concurrent.ThreadPoolExecutor;
//import java.util.concurrent.TimeUnit;
//
//import ch.tarsier.tarsier.network.client.TarsierMessagingClient;
//
///**
// * Created by amirreza on 10/26/14.
// */
//public class Server extends Thread{
//    public static final String TAG = "Server";
//    private final HashMap<Integer,TarsierMessagingClient.MyConnection> mConnectionsMap = new HashMap<Integer,TarsierMessagingClient.MyConnection>();
//    private final int THREAD_COUNT = 10;
//    private final static Random random = new Random();
//    ServerSocket socket = null;
//    private Handler handler;
//
//    private TarsierMessagingClient.MyConnection conn;
//
//    public Server(Handler handler) throws IOException {
//        try {
//            socket = new ServerSocket(WiFiDirectDebugActivity.SERVER_PORT);
//            this.handler = handler;
//            Log.d(TAG, "Socket Started");
//        } catch (IOException e) {
//            e.printStackTrace();
//            pool.shutdownNow();
//            throw e;
//        }
//    }
//    /**
//     * A ThreadPool for client sockets.
//     */
//    private final ThreadPoolExecutor pool = new ThreadPoolExecutor(
//            THREAD_COUNT, THREAD_COUNT, 10, TimeUnit.SECONDS,
//            new LinkedBlockingQueue<Runnable>());
//    @Override
//    public void run() {
//        while (true) {
//            try {
//                // A blocking operation.
//                conn = new TarsierMessagingClient.MyConnection(socket.accept(), handler, false);
//                pool.execute(conn);
//                mConnectionsMap.put(random.nextInt(1000),conn);
//                Log.d(TAG, "Launching the I/O handler");
//            } catch (IOException e) {
//                try {
//                    if (socket != null && !socket.isClosed())
//                        socket.close();
//                } catch (IOException ioe) {
//                }
//                e.printStackTrace();
//                pool.shutdownNow();
//                break;
//            }
//        }
//    }
//}
