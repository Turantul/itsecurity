package itsecurity.group5.common.beans;

public class PermissionCheckRequest extends InitiateSessionRequest {
    private Authentication user;

    public PermissionCheckRequest(InitiateSessionRequest initRequest) {
        terminal = initRequest.terminal;
    }

    public Authentication getUser() {
        return user;
    }

    public void setUser(Authentication user) {
        this.user = user;
    }
}
