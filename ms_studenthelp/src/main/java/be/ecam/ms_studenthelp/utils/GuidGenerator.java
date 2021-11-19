package be.ecam.ms_studenthelp.utils;

import java.util.UUID;

public class GuidGenerator {
    
    public static String GetNewUUIDString(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

}
