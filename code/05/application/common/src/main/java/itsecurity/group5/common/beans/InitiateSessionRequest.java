package itsecurity.group5.common.beans;

import java.io.Serializable;

public class InitiateSessionRequest implements Serializable {
    private Authentication terminal;
    private Authentication object;

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
