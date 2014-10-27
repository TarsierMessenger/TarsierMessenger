package ch.tarsier.tarsier;

import android.os.Handler;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by amirreza on 10/26/14.
 */
public class MyConnection implements Runnable {


    private static final String TAG = "MyConnection";
    private Socket socket = null;
    private Handler handler;

    private InputStream in;
    private OutputStream out;

    public MyConnection(Socket socket, Handler handler) {
        this.socket = socket;
        this.handler = handler;
    }

    @Override
    public void run() {
        try {
            in = socket.getInputStream();
            out= socket.getOutputStream();
            byte[] buffer = new byte[1024];
            int bytes;
            handler.obtainMessage(WiFiDirectDebugActivity.MY_HANDLE, this)
                    .sendToTarget();



            while (true) {
                try {
                    // Read from the InputStream
                    bytes = in.read(buffer);
                    if (bytes == -1) {
                        break;
                    }
                    // Send the obtained bytes to the UI Activity
                    Log.d(TAG, "Rec:" + String.valueOf(buffer));
                    handler.obtainMessage(WiFiDirectDebugActivity.MESSAGE_READ,
                            bytes, -1, buffer).sendToTarget();

                } catch (IOException e) {
                    Log.e(TAG, "disconnected", e);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public synchronized void write(byte[] buffer) {
        try {
            out.write(buffer);
        } catch (IOException e) {
            Log.e(TAG, "Exception during write", e);
        }
    }

}
