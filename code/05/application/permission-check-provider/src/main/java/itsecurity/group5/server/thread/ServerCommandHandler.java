package itsecurity.group5.server.thread;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import itsecurity.group5.common.beans.PermissionCheckRequest;
import itsecurity.group5.common.beans.PermissionCheckRequestResponse;
import itsecurity.group5.server.Server;

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

            PermissionCheckRequestResponse response = handleRequest(input);

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

    private PermissionCheckRequestResponse handleRequest(PermissionCheckRequest request) {
        System.out.println("Message: " + request.getMessage());
        PermissionCheckRequestResponse ret = new PermissionCheckRequestResponse();
        System.out.println("Calling pcp");
        ret.setResult(server.getPermissionCheckProvider().checkPermission(
            request.getUserId(), request.getUserAuth(),
            request.getTerminalId(), request.getTerminalAuth(),
            request.getObjectId(), request.getObjectAuth(),
            request.getPwHash(), request.getIrisData()));
        System.out.println("Result: " + ret.getResult());
        return ret;
    }
}
