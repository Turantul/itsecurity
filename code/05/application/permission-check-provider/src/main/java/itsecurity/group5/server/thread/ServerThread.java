package itsecurity.group5.server.thread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import itsecurity.group5.server.Server;

public class ServerThread extends Thread {
    private Server server;
    private ServerSocket socket;
    private int port;

    @Override
    public void run() {
        System.out.println("Server started");
        try {
            socket = new ServerSocket(port);
        } catch (Exception e) {
            e.printStackTrace();
        }
        while (!socket.isClosed()) {
            try {
                Socket inSocket = socket.accept();
                System.out.println("new connection");
                new ServerCommandHandler(inSocket, server).start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public void shutdown() {
        System.out.println("Shutdown ManagementThread");
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
