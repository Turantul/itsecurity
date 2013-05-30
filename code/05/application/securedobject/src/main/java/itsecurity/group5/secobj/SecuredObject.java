package itsecurity.group5.secobj;

public interface SecuredObject {
	
	public Boolean checkPermission(Integer userId, Byte[] userAuth, Integer terminalId, Byte[] terminalAuth,
			 String pinHash, Byte[] irisData);

}
