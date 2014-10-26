package ch.tarsier.tarsier;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by amirreza on 10/26/14.
 */
public class Client extends Thread{
    public final String TAG = "Client";
    private String serverMessage;
    public final InetAddress SERVER_IP ;

    public final int SERVER_PORT = 8888;
    private boolean mRun = false;

    BufferedOutputStream out;
    BufferedInputStream in;
    byte[] buffer = new byte[200];


    public Client(InetAddress serverIP) {
        SERVER_IP = serverIP;
    }

    public void sendMessage(byte[] message) throws IOException {
        if (out != null) {
            out.write(message);
        }
    }

    public byte[] recBytes(){
        return buffer;
    }
    public void stopClient() {
        mRun = false;
    }
    public void run() {
        mRun = true;
        try {

            Log.d("serverAddr : ", SERVER_IP.toString());
            Log.d("TCP Client", "C: Connecting...");
            // create a socket to make the connection with the server
            Socket socket = new Socket(SERVER_IP, SERVER_PORT);
            Log.d("TCP Server IP", SERVER_IP.toString());
            try {
                // send the message to the server
                out = new BufferedOutputStream(socket.getOutputStream(),1024);

                Log.d("TCP Client", "C: Sent.");
                // receive the message which the server sends back
                in = new BufferedInputStream(socket.getInputStream());
                // in this while the client listens for the messages sent by the
                // server
                    while (mRun) {
                        in.read(buffer);
                        Log.d(TAG,"Server: " + serverMessage);
                    }
            } catch (Exception e) {
                Log.e("TCP", "S: Error", e);
            } finally {
                socket.close();
            }
        } catch (Exception e) {
            Log.d("TCP", "C: Error", e);
        }
    }

}
