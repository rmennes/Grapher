package Grapher;

import java.io.FileNotFoundException;
import java.util.Vector;

import net.minidev.json.parser.ParseException;

public class PacketLoss extends Parser {

	public PacketLoss(String file) throws FileNotFoundException, ParseException {
		super(file);
	}

	@Override
	public Vector<Double> Get() {
		return GetIntervalInfo("lost_packets");
	}

	@Override
	public double Summary() {
		return GetEndInfo("lost_packets");
	}

	@Override
	public String measure() {
		return "Packets";
	}

}
