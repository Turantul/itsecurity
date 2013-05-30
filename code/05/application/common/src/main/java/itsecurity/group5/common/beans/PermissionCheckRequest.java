package itsecurity.group5.common.beans;

import java.io.Serializable;

public class PermissionCheckRequest implements Serializable{
	private Integer userId;
	private Byte[] userAuth;
	private Integer terminalId;
	private Byte[] terminalAuth;
	private Byte[] irisData;
	private String message;
	
	public PermissionCheckRequest(){
		this.message = "Hello World!";
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Byte[] getUserAuth() {
		return userAuth;
	}

	public void setUserAuth(Byte[] userAuth) {
		this.userAuth = userAuth;
	}

	public Integer getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(Integer terminalId) {
		this.terminalId = terminalId;
	}

	public Byte[] getTerminalAuth() {
		return terminalAuth;
	}

	public void setTerminalAuth(Byte[] terminalAuth) {
		this.terminalAuth = terminalAuth;
	}

	public Byte[] getIrisData() {
		return irisData;
	}

	public void setIrisData(Byte[] irisData) {
		this.irisData = irisData;
	}
}
