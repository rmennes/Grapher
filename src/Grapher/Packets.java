package Grapher;

import java.io.FileNotFoundException;
import java.util.Vector;

import net.minidev.json.parser.ParseException;

public class Packets extends Parser {

	public Packets(String file) throws FileNotFoundException, ParseException {
		super(file);
	}

	@Override
	public Vector<Double> Get() {
		return GetIntervalInfo("packets");
	}

	@Override
	public double Summary() {
		return GetEndInfo("packets");
	}

	@Override
	public String measure() {
		return "packets";
	}

}
