import java.util.*;
import java.lang.Thread;
import java.util.concurrent.*;

public class SingletonStorage extends Thread{
    private static SingletonStorage singStrg;

    private Semaphore sem;
    private Map<String, byte[]> listContent;
    
    private SingletonStorage(){
        sem = new Semaphore(1);
        listContent = new HashMap<String, byte[]>();
    }
    
    public static SingletonStorage get(){
        if(singStrg == null)
            singStrg = new SingletonStorage();
        return singStrg;
    }

    public Semaphore getSemaphore(){
        return sem;
    }

    public boolean storeBytes(String key, byte[] data){
        if(key.equals("no key"))
            return false;
        
        listContent.put(key, data);
        return true;
    }

    public byte[] get(String key){
        return listContent.get(key);
    }

    public boolean hasData(byte[] header_byte){
        String key = getKey(header_byte);
        
        if(key.equals("no key"))
            return false;
        if(listContent.containsKey(key))
            return false;

        return true;
    }

    public String getKey(byte[] header_byte){
        String[] headerStrs = (new String(header_byte)).split("\\n");
        StringTokenizer tokenizedLine;

        String key;

        if(headerStrs.length < 2)
            return "no key";
        
        tokenizedLine = new StringTokenizer(headerStrs[0]);
        if(!tokenizedLine.nextToken().equals("GET"))
            return "no key";
        key = tokenizedLine.nextToken();
        
        tokenizedLine = new StringTokenizer(headerStrs[1]);
        if(!tokenizedLine.nextToken().equals("Host:"))
            return "no key";
        key = tokenizedLine.nextToken() + key;

        return key;
    }
}
