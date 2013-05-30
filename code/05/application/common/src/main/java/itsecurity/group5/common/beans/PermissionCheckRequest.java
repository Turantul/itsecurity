package itsecurity.group5.common.beans;

import java.io.Serializable;

public class PermissionCheckRequest implements Serializable{
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
}
