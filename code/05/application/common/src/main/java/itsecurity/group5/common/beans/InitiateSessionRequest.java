package itsecurity.group5.common.beans;

import java.io.Serializable;

public class InitiateSessionRequest implements Serializable {
    protected Authentication terminal;

    public Authentication getTerminal() {
        return terminal;
    }

    public void setTerminal(Authentication terminal) {
        this.terminal = terminal;
    }
}
