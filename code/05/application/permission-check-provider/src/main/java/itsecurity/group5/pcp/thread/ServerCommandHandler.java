package itsecurity.group5.pcp.thread;

import itsecurity.group5.common.beans.InitiateSessionRequest;
import itsecurity.group5.common.beans.PermissionCheckRequest;
import itsecurity.group5.common.interfaces.IPermissionCheckProvider;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.UUID;

public class ServerCommandHandler implements Runnable {
    private Socket socket;
    private IPermissionCheckProvider provider;

    public ServerCommandHandler(Socket socket, IPermissionCheckProvider provider) {
        super();
        this.socket = socket;
        this.provider = provider;
    }

    @Override
    public void run() {
        try {
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            InitiateSessionRequest input = (InitiateSessionRequest) in.readObject();

            UUID uuid = provider.initateAuthorizationSession(input);

            ObjectOutputStream socketout = new ObjectOutputStream(socket.getOutputStream());
            socketout.writeObject(uuid);
            
            PermissionCheckRequest request = (PermissionCheckRequest) in.readObject();

            boolean response = provider.checkPermission(request);
            socketout.writeObject(response);
            
            socket.close();
        } catch (IOException e) {
            if (!socket.isClosed()) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
