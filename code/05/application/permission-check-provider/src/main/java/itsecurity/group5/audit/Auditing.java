package itsecurity.group5.audit;

import java.util.Date;

public class Auditing {
    public static void logError(String text) {
        System.out.println("ERROR: " + new Date() + " " + text);
    }
    
    public static void logInfo(String text) {
        System.out.println("INFO:  " + new Date() + " " + text);
    }
}
