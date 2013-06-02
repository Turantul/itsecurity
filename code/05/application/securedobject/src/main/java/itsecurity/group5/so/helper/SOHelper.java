package itsecurity.group5.so.helper;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.UUID;

import itsecurity.group5.common.beans.Authentication;
import itsecurity.group5.common.beans.InitiateSessionRequest;
import itsecurity.group5.common.beans.PermissionCheckRequest;
import itsecurity.group5.common.helper.SignatureHelper;
import itsecurity.group5.common.interfaces.IPermissionCheckProvider;

public class SOHelper {
    /**
     * This method creates a faked request from a terminal and acts as a decorator
     */
    public static boolean sendRequest(IPermissionCheckProvider secObj, int userId, int terminalId) {
        try {
            // First initiate the session
            InitiateSessionRequest initRequest = new InitiateSessionRequest();

            Authentication terminal = new Authentication();
            terminal.setCertificate(SignatureHelper.getCertificate("src/main/resources/terminal" + terminalId + ".p12"));
            terminal.setText("Authentication");
            terminal.calculateSignature(SignatureHelper.getPrivateKey("src/main/resources/terminal" + terminalId + ".p12"));
            initRequest.setTerminal(terminal);

            // And get a unique session ID
            UUID uuid = secObj.initateAuthorizationSession(initRequest);

            if (uuid != null) {
                // This is used to formulate a new request which holds this ID signed by the user
                PermissionCheckRequest request = new PermissionCheckRequest(initRequest);
    
                Authentication user = new Authentication();
                user.setCertificate(SignatureHelper.getCertificate("src/main/resources/user" + userId + ".p12"));
                user.setText("Authentication");
                // Add iris scan to every request
                byte[] b1 = { (byte) 0x03, (byte) 0x00, (byte) 0x00, (byte) 0x00 };
                user.setIrisData(b1);
                user.setUuid(uuid);
                user.calculateSignature(SignatureHelper.getPrivateKey("src/main/resources/user" + userId + ".p12"));
                request.setUser(user);
    
                return secObj.checkPermission(request);
            }
        } catch (UnrecoverableKeyException | KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException | InvalidKeyException
                | SignatureException e) {
            System.out.println("Error creating authentication data for user or terminal");
        }

        return false;
    }
}
