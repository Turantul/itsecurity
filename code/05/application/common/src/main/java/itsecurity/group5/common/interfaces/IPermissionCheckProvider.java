package itsecurity.group5.common.interfaces;

import java.util.UUID;

import itsecurity.group5.common.beans.InitiateSessionRequest;
import itsecurity.group5.common.beans.PermissionCheckRequest;

public interface IPermissionCheckProvider {
    UUID initateAuthorizationSession(InitiateSessionRequest request);
    
    boolean checkPermission(PermissionCheckRequest request);
}
