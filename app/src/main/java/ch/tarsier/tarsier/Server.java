package ch.tarsier.tarsier;

import android.os.Handler;
import android.util.Log;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by amirreza on 10/26/14.
 */
public class Server extends Thread {

    public static final String TAG = "Server";
    ServerSocket socket = null;
    private final int THREAD_COUNT = 10;
    private Handler handler;

    public Server(Handler handler) throws IOException {
        try {
            socket = new ServerSocket(HomeActivity.SERVER_PORT);
            this.handler = handler;
            Log.d(TAG, "Socket Started");
        } catch (IOException e) {
            e.printStackTrace();
            pool.shutdownNow();
            throw e;
        }
    }
    /**
     * A ThreadPool for client sockets.
     */
    private final ThreadPoolExecutor pool = new ThreadPoolExecutor(
            THREAD_COUNT, THREAD_COUNT, 10, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>());
    @Override
    public void run() {
        while (true) {
            try {
                // A blocking operation.
                pool.execute(new MyConnection(socket.accept(), handler));
                Log.d(TAG, "Launching the I/O handler");
            } catch (IOException e) {
                try {
                    if (socket != null && !socket.isClosed())
                        socket.close();
                } catch (IOException ioe) {
                }
                e.printStackTrace();
                pool.shutdownNow();
                break;
            }
        }
    }

    public interface MessageTarget {
        public Handler getHandler();
    }
}
