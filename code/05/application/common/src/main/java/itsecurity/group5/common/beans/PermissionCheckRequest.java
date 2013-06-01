package itsecurity.group5.common.beans;

import java.io.Serializable;

public class PermissionCheckRequest implements Serializable {
    private Authentication user;
    private byte[] irisData;
    private Authentication terminal;
    private Authentication object;

    public Authentication getUser() {
        return user;
    }

    public void setUser(Authentication user) {
        this.user = user;
    }

    public byte[] getIrisData() {
        return irisData;
    }

    public void setIrisData(byte[] irisData) {
        this.irisData = irisData;
    }

    public Authentication getTerminal() {
        return terminal;
    }

    public void setTerminal(Authentication terminal) {
        this.terminal = terminal;
    }

    public Authentication getObject() {
        return object;
    }

    public void setObject(Authentication object) {
        this.object = object;
    }
}
