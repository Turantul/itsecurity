package itsecurity.group5.client;

import itsecurity.group5.client.thread.RequestResponseListener;
import itsecurity.group5.common.beans.PermissionCheckRequest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client
{
	private int serverPort;
	private String serverAddress;
	
    public static void main(String[] args)
    {
    	if(args.length!=2){
    		System.out.println("Wrong usage: <serverAddress> <serverPort>");
    	}else{
    		Integer serverPort = Integer.parseInt(args[1]);
    		String serverAddress = args[0];
    		new Client(serverAddress, serverPort);
    	}
        
    }
    
    public Client(String serverAddress, Integer serverPort){
    	this.serverAddress = serverAddress;
    	this.serverPort = serverPort;
    	startConsole();
    }
    
    private void startConsole(){
    	System.out.println("Welcome to the Secured Object test console.");
        System.out.println("Type !help to get information about the available commands");

        InputStreamReader cin = new InputStreamReader(System.in);
        BufferedReader bin = new BufferedReader(cin);

        while (true)
        {
            try
            {
                String in = bin.readLine();
                if (in.startsWith("!help"))
                {
                	String split[] = in.split(" ");
                	if(split.length == 1){
	                    System.out.println("Available commands are:");
	                    System.out.println("!exit - exit the program");
	                    System.out.println("!test - send test message");
	                    System.out.println("Use the !help <command> to get information about the command");
                	}else{
	                	switch(split[1]){
		                	case "!exit": 	System.out.println("Ends the management program");
		                					break;
		                	default: 		System.out.println("Command unknown. Try !help");
	                	}
                	}
                }
                
                if (in.equals("!exit"))
                {
                    System.out.println("Exiting the management program");
                    break;
                }
                if(in.startsWith("!test")){
                	String split[] = in.split(" ");
                	String message = "Hello World!";
                	PermissionCheckRequest request = new PermissionCheckRequest();
                	if(split.length==3){
                		request.setUserId(Integer.parseInt(split[1]));
                		request.setTerminalId(Integer.parseInt(split[2]));
                	}
                	request.setMessage(message);
                	Socket socket = new Socket(serverAddress, serverPort);
                	ObjectOutputStream socketout = new ObjectOutputStream(socket.getOutputStream());
                    socketout.writeObject(request);
                    new RequestResponseListener(socket).start();
                }
                
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}

