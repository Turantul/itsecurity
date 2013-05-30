package itsecurity.group5.server;

import itsecurity.group5.server.authobj.AuthObjManagement;

public class PermissionCheckProviderImpl implements PermissionCheckProvider{

	private AuthObjManagement aom = new AuthObjManagement();
	
	@Override
	public Boolean checkPermission(Integer userId, Byte[] userAuth,
			Integer terminalId, Byte[] terminalAuth, Byte[] irisData) {
		String zone = aom.auhtorizeTerminal(terminalId);
		if(zone!=null){
			if(aom.authorizeZone(zone)){
				return aom.authorizeUser(userId, zone);
			}
		}
		return false;
	}

}
