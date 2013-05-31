package itsecurity.group5.client.thread;

import itsecurity.group5.common.beans.PermissionCheckRequestResponse;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class RequestResponseListener extends Thread {

    private Socket socket;

    public RequestResponseListener(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            PermissionCheckRequestResponse response = (PermissionCheckRequestResponse) in.readObject();
            System.out.println(response.getResult());
            socket.close();
        } catch (IOException e) {
            if (!socket.isClosed()) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void shutdown() {
        System.out.println("Shutdown RequestResponseListener");
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
