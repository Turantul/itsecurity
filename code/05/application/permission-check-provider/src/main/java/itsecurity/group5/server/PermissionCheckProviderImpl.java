package itsecurity.group5.server;

import itsecurity.group5.server.authobj.AuthObjManagement;

public class PermissionCheckProviderImpl implements PermissionCheckProvider{

	private AuthObjManagement aom = new AuthObjManagement();
	
	@Override
	public Boolean checkPermission(Integer userId, Byte[] userAuth, Integer terminalId, Byte[] terminalAuth,
									Integer objectId, Byte[] objectAuth, String pwHash, Byte[] irisData) {
		if(!authenticateObject(objectAuth, "")){
			//Call auditing
			return false;
		}
		if(!authenticateTerminal(terminalAuth, "")){
			//Call auditing
			return false;
		}
		if(!authenticateUser(userAuth, "")){
			//Call auditing
			return false;
		}
		Integer zone = aom.auhtorizeTerminal(terminalId);
		if(zone!=null){
			if(aom.authorizeZone(objectId, zone)){
				Boolean result = aom.authorizeUser(userId, zone, pwHash, irisData);
				//Call auditing
				return result;
			}
		}
		//Call auditing
		return false;
	}
	
	private Boolean authenticateObject(Byte[] objectAuth, String objectCertificate){
		//TODO: Implement Certificate check
		return true;
	}
	private Boolean authenticateTerminal(Byte[] terminalAuth, String terminalCertificate){
		//TODO: Implement Certificate check
		return true;
	}
	private Boolean authenticateUser(Byte[] userAuth, String userCertificate){
		//TODO: Implement Certificate check
		return true;
	}

}
