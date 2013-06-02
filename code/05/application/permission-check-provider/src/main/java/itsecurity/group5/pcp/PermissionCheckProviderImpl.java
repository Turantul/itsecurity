package itsecurity.group5.pcp;

import itsecurity.group5.aom.AuthObjManagement;
import itsecurity.group5.audit.Auditing;
import itsecurity.group5.common.beans.PermissionCheckRequest;

public class PermissionCheckProviderImpl implements PermissionCheckProvider {
    private AuthObjManagement aom = new AuthObjManagement();

    @Override
    public boolean checkPermission(PermissionCheckRequest request) {
        if (!request.getObject().verifySignature("Authentication")) {
            Auditing.logError("Authentication of object failed: " + request.getObject().getCertificate().getIssuerDN().getName());
            return false;
        }
        if (!request.getTerminal().verifySignature("Authentication")) {
            Auditing.logError("Authentication of terminal failed: " + request.getTerminal().getCertificate().getIssuerDN().getName());
            return false;
        }
        if (!request.getUser().verifySignature("Authentication")) {
            Auditing.logError("Authentication of user failed: " + request.getUser().getCertificate().getIssuerDN().getName());
            return false;
        }

        Integer zone = aom.auhtorizeTerminal(request.getTerminal().getId());
        if (zone != null) {
            if (aom.authorizeZone(request.getObject().getId(), zone)) {
                boolean result = aom.authorizeUser(request.getUser().getId(), zone, request.getUser().getIrisData());

                if (result) {
                    Auditing.logInfo("The authentication was successful. User: " + request.getUser().getId() + ", Zone: " + zone);
                }
                else {
                    Auditing.logError("The authentication failed. User: " + request.getUser().getId() + ", Zone: " + zone);
                }

                return result;
            }
            else {
                Auditing.logError("The zone '" + zone + "' is not attached to the object '" + request.getObject().getId() + "'.");
            }
        }
        else {
            Auditing.logError("The terminal '" + request.getTerminal().getId() + "' has no attached zone.");
        }

        return false;
    }
}
