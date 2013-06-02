package itsecurity.group5.so;

import itsecurity.group5.common.beans.InitiateSessionRequest;
import itsecurity.group5.common.beans.PermissionCheckRequest;
import itsecurity.group5.common.interfaces.IPermissionCheckProvider;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.UUID;

import javax.net.ssl.SSLSocketFactory;

public class SecuredObjectSocketImpl implements IPermissionCheckProvider {
    private int serverPort;
    private String serverAddress;
    
    private Socket socket;
    private ObjectOutputStream socketout;
    private ObjectInputStream in;

    public SecuredObjectSocketImpl(String serverAddress, Integer serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }

    @Override
    public UUID initateAuthorizationSession(InitiateSessionRequest request) {
        try {
            socket = SSLSocketFactory.getDefault().createSocket(serverAddress, serverPort);
            socketout = new ObjectOutputStream(socket.getOutputStream());
            socketout.writeObject(request);

            in = new ObjectInputStream(socket.getInputStream());
            UUID response = (UUID) in.readObject();

            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }

    @Override
    public boolean checkPermission(PermissionCheckRequest request) {
        if (socket == null || socket.isClosed()) {
            return false;
        }
        
        try {
            socketout.writeObject(request);

            Boolean response = (Boolean) in.readObject();
            socket.close();

            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
