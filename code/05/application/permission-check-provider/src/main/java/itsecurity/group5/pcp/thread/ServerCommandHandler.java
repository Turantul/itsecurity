package itsecurity.group5.pcp.thread;

import itsecurity.group5.common.beans.PermissionCheckRequest;
import itsecurity.group5.pcp.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerCommandHandler implements Runnable {
    private Socket socket;
    private Server server;

    public ServerCommandHandler(Socket socket, Server server) {
        super();
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            PermissionCheckRequest input = (PermissionCheckRequest) in.readObject();

            boolean response = server.getPermissionCheckProvider().checkPermission(input);

            ObjectOutputStream socketout = new ObjectOutputStream(socket.getOutputStream());
            socketout.writeObject(response);
        } catch (IOException e) {
            if (!socket.isClosed()) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
