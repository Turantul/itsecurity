package itsecurity.group5.server;

public interface PermissionCheckProvider {
    Boolean checkPermission(Integer userId, Byte[] userAuth, Integer terminalId, Byte[] terminalAuth, Integer objectId, Byte[] objectAuth, Byte[] irisData);
}
