package itsecurity.group5.server;

import itsecurity.group5.server.thread.ServerThread;

public class Server {
    private ServerThread serverthread = new ServerThread();
    private int port;
    private PermissionCheckProvider pcp = new PermissionCheckProviderImpl();

    public static void main(String[] args) {
        // Read Config and Setup server

        if (args.length != 1) {
            System.out.println("Wrong usage: <Port>");
        } else {
            Integer port = Integer.parseInt(args[0]);
            new Server(port);
        }
    }

    public Server(Integer port) {
        this.port = port;
        initialize();
    }

    public void initialize() {
        try {
            serverthread.setPort(port);
            serverthread.setServer(this);
            serverthread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public PermissionCheckProvider getPermissionCheckProvider() {
        return pcp;
    }
}
