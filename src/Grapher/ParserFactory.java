package Grapher;

import java.io.FileNotFoundException;

import net.minidev.json.parser.ParseException;

public class ParserFactory {
	
	public static Parser getParser(String type, String file) throws FileNotFoundException, ParseException{
		if(type.toLowerCase().equals("throughput")) return new Throughput(file);
		return null;
	}

}
