package Grapher;

import java.io.FileNotFoundException;

import net.minidev.json.parser.ParseException;

public class ParserFactory {
	
	public static Parser getParser(String type, String file) throws FileNotFoundException, ParseException{
		String typeToCheck = type.toLowerCase();
		if(typeToCheck.equals("throughput")) return new Throughput(file);
		if(typeToCheck.equals("jitter")) return new Jitter(file);
		if(typeToCheck.equals("packet")) return new Packets(file);
		if(typeToCheck.equals("packetloss")) return new PacketLoss(file);
		if(typeToCheck.equals("lost")) return new Lost(file);
		if(typeToCheck.equals("speed")) return new Speed(file);
		return null;
	}

}
