package itsecurity.group5.pcp;

import java.util.UUID;

import itsecurity.group5.aom.AuthObjManagement;
import itsecurity.group5.audit.Auditing;
import itsecurity.group5.common.beans.InitiateSessionRequest;
import itsecurity.group5.common.beans.PermissionCheckRequest;
import itsecurity.group5.common.interfaces.IPermissionCheckProvider;

public class PermissionCheckProviderImpl implements IPermissionCheckProvider {
    private AuthObjManagement aom = new AuthObjManagement();
    
    private UUID uuid;
    private Integer objectId;
    
    public PermissionCheckProviderImpl(String identifyer) {
        objectId = Integer.parseInt(identifyer.split(" ")[1]);
    }

    @Override
    public UUID initateAuthorizationSession(InitiateSessionRequest request) {
        // Authentication of Terminal
        if (!request.getTerminal().verifySignature("Authentication")) {
            Auditing.logError("Authentication of terminal failed: " + request.getTerminal().getCertificate().getIssuerDN().getName());
            return null;
        }

        // Autorization of Terminal and Zone
        Integer zone = aom.auhtorizeTerminal(request.getTerminal().getId());
        if (zone != null) {
            if (aom.authorizeZone(objectId, zone)) {
                // TODO: ensure that this number is purely random and unique systemwide
                uuid = UUID.randomUUID();
                return uuid;
            }
            else {
                Auditing.logError("The zone '" + zone + "' is not attached to the object '" + objectId + "'.");
            }
        }
        else {
            Auditing.logError("The terminal '" + request.getTerminal().getId() + "' has no attached zone.");
        }

        return null;
    }

    @Override
    public boolean checkPermission(PermissionCheckRequest request) {
        // Authentication of Terminal and User (complete mediation)
        if (!request.getTerminal().verifySignature("Authentication")) {
            Auditing.logError("Authentication of terminal failed: " + request.getTerminal().getCertificate().getIssuerDN().getName());
            return false;
        }
        if (!request.getUser().verifySignature("Authentication")) {
            Auditing.logError("Authentication of user failed: " + request.getUser().getCertificate().getIssuerDN().getName());
            return false;
        }
        
        // Check if session ID is equal
        if (request.getUser().getUuid().compareTo(uuid) != 0) {
            Auditing.logError("Session has been altered. Got: " + request.getUser().getUuid() + ", Wanted: " + uuid);
            return false;
        }

        Integer zone = aom.auhtorizeTerminal(request.getTerminal().getId());
        if (zone != null) {
            if (aom.authorizeZone(objectId, zone)) {
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
                Auditing.logError("The zone '" + zone + "' is not attached to the object '" + objectId + "'.");
            }
        }
        else {
            Auditing.logError("The terminal '" + request.getTerminal().getId() + "' has no attached zone.");
        }

        return false;
    }
}
