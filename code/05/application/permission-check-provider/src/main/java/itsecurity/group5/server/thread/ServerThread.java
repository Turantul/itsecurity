package itsecurity.group5.server.thread;

import itsecurity.group5.server.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.security.cert.Certificate;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.security.cert.X509Certificate;

public class ServerThread extends Thread {
    private Server server;
    private ServerSocket socket;
    private int port;
    
    private ExecutorService pool = Executors.newCachedThreadPool();

    @Override
    public void run() {
        System.out.println("Starting server...");
        try {
            socket = SSLServerSocketFactory.getDefault().createServerSocket(port);
            ((SSLServerSocket) socket).setNeedClientAuth(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        System.out.println("Listening to requests...");
        
        while (!socket.isClosed()) {
            try {
                SSLSocket inSocket = (SSLSocket) socket.accept();
                System.out.println("New connection received...");
                
                X509Certificate[] certs = inSocket.getSession().getPeerCertificateChain();
                String name = certs[0].getSubjectDN().getName();
                System.out.println("Dealing with '" + name.subSequence(3, name.indexOf(", OU=ESSE")) + "'");
                
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
