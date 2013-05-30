package itsecurity.group5.secobj;

import itsecurity.group5.client.thread.RequestResponseListener;
import itsecurity.group5.common.beans.PermissionCheckRequest;

import java.io.ObjectOutputStream;
import java.net.Socket;

public class SecuredObjectSocketImpl implements SecuredObject{

	private int serverPort;
	private Integer objectId;
	private String serverAddress;
	
	public SecuredObjectSocketImpl(String serverAddress, Integer serverPort, Integer objectId){
		this.serverAddress = serverAddress;
    	this.serverPort = serverPort;
    	this.objectId = objectId;
	}
	
	@Override
	public Boolean checkPermission(Integer userId, Byte[] userAuth,
			Integer terminalId, Byte[] terminalAuth, String pinHash,
			Byte[] irisData) {
		PermissionCheckRequest request = new PermissionCheckRequest();
		request.setUserId(userId);
		request.setUserAuth(userAuth);
		request.setTerminalId(terminalId);
		request.setTerminalAuth(terminalAuth);
		//Objects adds its own information to requestdata from Terminal
		request.setObjectId(objectId);
		request.setObjectAuth(null);
		request.setPwHash(pinHash);
		request.setIrisData(irisData);
		try{
	    	Socket socket = new Socket(this.serverAddress, this.serverPort);
	    	ObjectOutputStream socketout = new ObjectOutputStream(socket.getOutputStream());
	        socketout.writeObject(request);
	        new RequestResponseListener(socket).start();
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}

}
