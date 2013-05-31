package itsecurity.group5.server.thread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import itsecurity.group5.server.Server;

public class ServerThread extends Thread {
    private Server server;
    private ServerSocket socket;
    private int port;
    
    private ExecutorService pool = Executors.newCachedThreadPool();

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
                System.out.println("New connection received...");
                pool.execute(new ServerCommandHandler(inSocket, server));
            } catch (SocketException e) {
                if (!socket.isClosed()) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
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
        try {
            socket.close();
            pool.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
