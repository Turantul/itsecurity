package itsecurity.group5.so;

import itsecurity.group5.common.beans.PermissionCheckRequest;

public interface SecuredObject {
    boolean checkPermission(PermissionCheckRequest request);
}
