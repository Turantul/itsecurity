package itsecurity.group5.pcp;

import itsecurity.group5.common.helper.PropertyHelper;
import itsecurity.group5.pcp.thread.ServerThread;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Server {
    private ServerThread serverthread = new ServerThread();
    private int port;
    private PermissionCheckProvider pcp = new PermissionCheckProviderImpl();

    public static void main(String[] args) {
        // Read config and setup server

        if (args.length != 1) {
            System.out.println("Wrong usage: <Port>");
        } else {
            new Server(Integer.parseInt(args[0]));
        }
    }

    public Server(Integer port) {
        this.port = port;
        
        PropertyHelper.loadProperties();
        
        initialize();
        startConsole();
    }

    private void initialize() {
        try {
            serverthread.setPort(port);
            serverthread.setServer(this);
            serverthread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void startConsole() {
        System.out.println("Welcome to the Permission-Check-Provider console.");
        System.out.println("Type !help to get information about the available commands");

        InputStreamReader cin = new InputStreamReader(System.in);
        BufferedReader bin = new BufferedReader(cin);

        while (true) {
            try {
                String in = bin.readLine();
                if (in.startsWith("!help")) {
                    String split[] = in.split(" ");
                    if (split.length == 1) {
                        System.out.println("Available commands are:");
                        System.out.println("!exit - exit the program");
                        System.out.println("Use the !help <command> to get information about the command");
                    } else {
                        switch (split[1]) {
                            case "!exit":
                                System.out.println("Ends the management program");
                                break;
                            default:
                                System.out.println("Command unknown. Try !help");
                        }
                    }
                }

                if (in.equals("!exit")) {
                    System.out.println("Shutting down the server...");
                    serverthread.shutdown();
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public PermissionCheckProvider getPermissionCheckProvider() {
        return pcp;
    }
}
