package itsecurity.group5.so;

import itsecurity.group5.common.beans.Authentication;
import itsecurity.group5.common.beans.PermissionCheckRequest;
import itsecurity.group5.common.beans.PermissionCheckRequestResponse;
import itsecurity.group5.common.helper.SignatureHelper;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ssl.SSLSocketFactory;

public class SecuredObjectSocketImpl implements SecuredObject {
    private int serverPort;
    private String serverAddress;

    public SecuredObjectSocketImpl(String serverAddress, Integer serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }

    @Override
    public boolean checkPermission(PermissionCheckRequest request) {
        try {
            // Objects adds its own information to requestdata from Terminal
            Authentication auth = new Authentication();
            auth.setText("Authentication");
            auth.setCertificate(SignatureHelper.getCertificate());
            auth.calculateSignature(SignatureHelper.getKeyPair().getPrivate());
            request.setObject(auth);

            try {
                Socket socket = SSLSocketFactory.getDefault().createSocket(serverAddress, serverPort);
                ObjectOutputStream socketout = new ObjectOutputStream(socket.getOutputStream());
                socketout.writeObject(request);

                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                PermissionCheckRequestResponse response = (PermissionCheckRequestResponse) in.readObject();
                socket.close();

                return response.getResult();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (UnrecoverableKeyException | KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException | InvalidKeyException | SignatureException e1) {
            e1.printStackTrace();
        }

        return false;
    }
}
