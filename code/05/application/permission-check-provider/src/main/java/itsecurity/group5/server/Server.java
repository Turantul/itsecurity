package itsecurity.group5.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

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
            new Server(Integer.parseInt(args[0]));
        }
    }

    public Server(Integer port) {
        this.port = port;
        
        loadProperties();
        
        initialize();
        startConsole();
    }
    
    private void loadProperties() {
        try {
            Properties properties = new Properties();
            InputStream stream = getClass().getClassLoader().getResourceAsStream("config.properties");
            properties.load(stream);
            stream.close();
            
            for (Object key : properties.keySet()) {
                System.setProperty(key.toString(), properties.getProperty(key.toString()));
            }
        } catch (IOException e) {
            System.out.println("Error loading properties");
            System.exit(-1);
        }
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
