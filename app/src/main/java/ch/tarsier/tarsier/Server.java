package ch.tarsier.tarsier;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by amirreza on 10/26/14.
 */
public class Server extends Thread {
    private static final String TAG = "Server";
    private Socket client;
    private ServerSocket mSocket;
    boolean serverOn;
    private static final ArrayList<MyConnection> clients = new ArrayList<MyConnection>();

    public Server() {
        serverOn = true;
    }

    @Override
    public void run() {
        try {
            mSocket = new ServerSocket(8888);
        } catch (IOException e) {
            Log.w(TAG, "Server faield to start,socket error");
            e.printStackTrace();
        }

        Log.i(TAG, "Server started ...");


        while (serverOn) {
            try {
                client = mSocket.accept();
                MyConnection connection = new MyConnection(client);
                connection.start();
                clients.add(connection);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Log.d(TAG, "Server stopped");
    }

}
