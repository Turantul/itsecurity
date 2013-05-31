package itsecurity.group5.client;

import itsecurity.group5.secobj.SecuredObject;
import itsecurity.group5.secobj.SecuredObjectSocketImpl;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Client {
    private SecuredObject secObj;

    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Wrong usage: <serverAddress> <serverPort> <ObjectId>");
        } else {
            Integer serverPort = Integer.parseInt(args[1]);
            String serverAddress = args[0];
            Integer objectId = Integer.parseInt(args[2]);
            new Client(serverAddress, serverPort, objectId);
        }
    }

    public Client(String serverAddress, Integer serverPort, Integer objectId) {
        this.secObj = new SecuredObjectSocketImpl(serverAddress, serverPort, objectId);
        startConsole();
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
                    String message = "Hello World!";
                    if (split.length == 3) {
                        Boolean response =
                            secObj.checkPermission(Integer.parseInt(split[1]), null, Integer.parseInt(split[2]), null,
                                "test", null);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
