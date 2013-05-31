package itsecurity.group5.server;

public interface PermissionCheckProvider {
    public Boolean checkPermission(Integer userId, Byte[] userAuth, Integer terminalId, Byte[] terminalAuth, Integer objectId, Byte[] objectAuth,
            String pinHash, Byte[] irisData);
}
