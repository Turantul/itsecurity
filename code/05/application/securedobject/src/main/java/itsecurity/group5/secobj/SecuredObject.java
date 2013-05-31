package itsecurity.group5.secobj;

public interface SecuredObject {
    Boolean checkPermission(Integer userId, Byte[] userAuth, Integer terminalId, Byte[] terminalAuth, Byte[] irisData);
}
