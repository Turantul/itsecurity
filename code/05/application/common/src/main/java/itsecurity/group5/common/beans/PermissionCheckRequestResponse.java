package itsecurity.group5.common.beans;

import java.io.Serializable;

public class PermissionCheckRequestResponse implements Serializable {
    private boolean result = false;

    public boolean getResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
