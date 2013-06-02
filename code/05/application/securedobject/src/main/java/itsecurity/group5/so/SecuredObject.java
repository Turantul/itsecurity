package itsecurity.group5.so;

import java.util.UUID;

import itsecurity.group5.common.beans.PermissionCheckRequest;

public interface SecuredObject {
    UUID initateAuthorizationSession(PermissionCheckRequest request);
    
    boolean checkPermission(PermissionCheckRequest request);
}
