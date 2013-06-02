package itsecurity.group5.pcp.thread;

import itsecurity.group5.audit.Auditing;
import itsecurity.group5.pcp.PermissionCheckProviderImpl;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.security.cert.X509Certificate;

public class ServerThread extends Thread {
    private ServerSocket socket;
    private int port;
    
    private ExecutorService pool = Executors.newCachedThreadPool();

    @Override
    public void run() {
        Auditing.logInfo("Starting server...");
        try {
            socket = SSLServerSocketFactory.getDefault().createServerSocket(port);
            ((SSLServerSocket) socket).setNeedClientAuth(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        Auditing.logInfo("Listening to requests...");
        
        while (!socket.isClosed()) {
            try {
                SSLSocket inSocket = (SSLSocket) socket.accept();
                Auditing.logInfo("New connection received...");
                
                X509Certificate[] certs = inSocket.getSession().getPeerCertificateChain();
                String name = certs[0].getSubjectDN().getName();
                String identifyer = name.subSequence(3, name.indexOf(", OU=ESSE")).toString();
                
                if (identifyer.startsWith("SecuredObject")) {
                    Auditing.logInfo("Dealing with '" + identifyer + "'");
                    pool.execute(new ServerCommandHandler(inSocket, new PermissionCheckProviderImpl(identifyer)));
                }
                else {
                    Auditing.logError("Got a request from a non-Secured Object: " + identifyer);
                }
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

    public void shutdown() {
        try {
            socket.close();
            pool.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
