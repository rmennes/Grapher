package Grapher;

import java.io.FileNotFoundException;
import java.util.Vector;

import net.minidev.json.parser.ParseException;

public class Lost extends Parser {

	public Lost(String file) throws FileNotFoundException, ParseException {
		super(file);
	}

	@Override
	public Vector<Double> Get() {
		return GetIntervalInfo("lost_percent");
	}

	@Override
	public double Summary() {
		return GetEndInfo("lost_percent");
	}

	@Override
	public String measure() {
		return "%";
	}

}
