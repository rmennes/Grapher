package Grapher;

import java.io.FileNotFoundException;
import java.util.Vector;

import net.minidev.json.parser.ParseException;

public class Jitter extends Parser {

	public Jitter(String file) throws FileNotFoundException, ParseException {
		super(file);
	}

	@Override
	public Vector<Double> Get() {
		return GetIntervalInfo("jitter_ms");
	}

	@Override
	public double Summary() {
		return GetEndInfo("jitter_ms");
	}

	@Override
	public String measure() {
		return "ms";
	}

}
