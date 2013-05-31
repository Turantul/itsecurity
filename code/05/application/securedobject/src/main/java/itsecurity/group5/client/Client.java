package itsecurity.group5.client;

import itsecurity.group5.secobj.SecuredObject;
import itsecurity.group5.secobj.SecuredObjectSocketImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class Client {
    private SecuredObject secObj;

    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Wrong usage: <serverAddress> <serverPort> <ObjectId>");
        } else {
            String serverAddress = args[0];
            Integer serverPort = Integer.parseInt(args[1]);
            Integer objectId = Integer.parseInt(args[2]);
            new Client(serverAddress, serverPort, objectId);
        }
    }

    public Client(String serverAddress, Integer serverPort, Integer objectId) {
        loadProperties();
        
        this.secObj = new SecuredObjectSocketImpl(serverAddress, serverPort, objectId);
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

    private void startConsole() {
        System.out.println("Welcome to the Secured Object test console.");
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
                        System.out.println("!test - send test message");
                        System.out.println("Use the !help <command> to get information about the command");
                    } else {
                        switch (split[1]) {
                            case "!exit":
                                System.out.println("Ends the management program");
                                break;
                            case "!test":
                                System.out.println("Usage: <UserID> <TerminalID>");
                                break;
                            default:
                                System.out.println("Command unknown. Try !help");
                        }
                    }
                }

                if (in.equals("!exit")) {
                    System.out.println("Exiting the management program");
                    break;
                }
                if (in.startsWith("!test")) {
                    String split[] = in.split(" ");
                    if (split.length == 3) {
                        Boolean response = secObj.checkPermission(Integer.parseInt(split[1]), null, Integer.parseInt(split[2]), null, null);
                        System.out.println("The autorization was " + (response ? "successful" : "unsuccessful") + ".");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
