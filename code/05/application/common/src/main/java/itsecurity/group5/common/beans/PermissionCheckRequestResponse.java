package itsecurity.group5.common.beans;

import java.io.Serializable;

public class PermissionCheckRequestResponse implements Serializable{
	private Boolean result;

	public Boolean getResult() {
		return result;
	}

	public void setResult(Boolean result) {
		this.result = result;
	}
}
