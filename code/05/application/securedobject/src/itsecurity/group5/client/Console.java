package itsecurity.group5.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Console
{
    public static void main(String[] args)
    {
    	if(args.length!=0){
    		System.out.println("Wrong usage:");
    	}else{
    		new Console();
    	}
        
    }
    
    public Console(){
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
                
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
