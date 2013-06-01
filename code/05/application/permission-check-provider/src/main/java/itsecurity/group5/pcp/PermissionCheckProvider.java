package itsecurity.group5.pcp;

import itsecurity.group5.common.beans.PermissionCheckRequest;

public interface PermissionCheckProvider {
    boolean checkPermission(PermissionCheckRequest request);
}
