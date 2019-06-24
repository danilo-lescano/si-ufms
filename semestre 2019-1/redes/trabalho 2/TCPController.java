import java.io.*;
import java.net.*;
import java.util.*;

public class TCPController{
	public static Map<String, TCPController> mapaCon = new HashMap<String, TCPController>();

	public static void addPack(DatagramPacket pack){
		
	}

	byte[] x = new byte[8];
	List<byte []> listaPack = new ArrayList<byte []>();
	public TCPController(){
		x[0] = 0x5a; x[4] = 0x4e;
		x[1] = 0x5b; x[5] = 0x2f;
		x[2] = 0x5c; x[6] = 0x61;
		x[3] = 0x5d; x[7] = 0x72;
		for(int i = 0; i < x.length; i++)
			System.out.println(x[i]);
	}
}