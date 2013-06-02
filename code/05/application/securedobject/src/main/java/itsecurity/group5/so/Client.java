package itsecurity.group5.so;

import itsecurity.group5.common.helper.PropertyHelper;
import itsecurity.group5.common.interfaces.IPermissionCheckProvider;
import itsecurity.group5.so.helper.SOHelper;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Client {
    private IPermissionCheckProvider secObj;

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Wrong usage: <serverAddress> <serverPort>");
        } else {
            String serverAddress = args[0];
            Integer serverPort = Integer.parseInt(args[1]);
            new Client(serverAddress, serverPort);
        }
    }

    public Client(String serverAddress, Integer serverPort) {
        PropertyHelper.loadProperties();

        this.secObj = new SecuredObjectSocketImpl(serverAddress, serverPort);
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
                        boolean response = SOHelper.sendRequest(secObj, Integer.parseInt(split[1]), Integer.parseInt(split[2]));
                        System.out.println("The autorization was " + (response ? "successful" : "unsuccessful") + ".");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
