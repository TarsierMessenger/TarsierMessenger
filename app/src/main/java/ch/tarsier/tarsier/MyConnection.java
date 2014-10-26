package ch.tarsier.tarsier;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by amirreza on 10/26/14.
 */
public class MyConnection extends Thread {

    private static final String TAG = "MyConnection";
    private Socket mClient;
    private byte[] buffer = new byte[1024];
    BufferedOutputStream out;
    BufferedInputStream in;

    private boolean mRun;

    public MyConnection(Socket client) {
        Log.i(TAG, "A new TCP connection established completely.");
        mClient = client;
        mRun = true;
    }

    public void sendMessage(byte[] message) throws IOException {
        if (out != null) {
            out.write(message);
        }
    }

    public byte[] recBytes(){
        return buffer;
    }
    public void stopServer() {
        mRun = false;
    }

    @Override
    public void run() {

        try {
            out =
                    new BufferedOutputStream(mClient.getOutputStream(), 1024);
            in = new BufferedInputStream(
                    mClient.getInputStream());


            while (true) {
                try {
                    in.read(buffer);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }

    }
}
