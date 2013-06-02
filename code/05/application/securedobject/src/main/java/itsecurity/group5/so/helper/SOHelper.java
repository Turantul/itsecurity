package itsecurity.group5.so.helper;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import itsecurity.group5.common.beans.Authentication;
import itsecurity.group5.common.beans.PermissionCheckRequest;
import itsecurity.group5.common.helper.SignatureHelper;
import itsecurity.group5.so.SecuredObject;

public class SOHelper {
    /**
     * This method creates a faked request from a terminal
     */
    public static boolean sendRequest(SecuredObject secObj, int userId, int terminalId) {
        PermissionCheckRequest request = new PermissionCheckRequest();
        try {
            Authentication user = new Authentication();
            user.setCertificate(SignatureHelper.getCertificate("src/main/resources/user" + userId + ".p12"));
            user.setText("Authentication");
            user.calculateSignature(SignatureHelper.getKeyPair("src/main/resources/user" + userId + ".p12").getPrivate());

            request.setUser(user);

            Authentication terminal = new Authentication();
            terminal.setCertificate(SignatureHelper.getCertificate("src/main/resources/terminal" + terminalId + ".p12"));
            terminal.setText("Authentication");
            terminal.calculateSignature(SignatureHelper.getKeyPair("src/main/resources/terminal" + terminalId + ".p12").getPrivate());
            request.setTerminal(terminal);

            // Add iris scan to every request
            byte[] b1 = { (byte) 0x03, (byte) 0x00, (byte) 0x00, (byte) 0x00 };
            request.setIrisData(b1);

            return secObj.checkPermission(request);
        } catch (UnrecoverableKeyException | KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException | InvalidKeyException
                | SignatureException e) {
            System.out.println("Error creating authentication data for user or terminal");
        }

        return false;
    }
}
