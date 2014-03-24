package Grapher;

import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Vector;

import net.minidev.json.parser.ParseException;

public class Throughput extends Parser {

	public Throughput(String file) throws FileNotFoundException, ParseException {
		super(file);
		
	}

	@Override
	public Vector<Double> Get() {
		Vector<Double> bytes = GetIntervalInfo("bytes");
		Vector<Double> megabytes = new Vector<Double>(bytes.size());
		Iterator<Double> it = bytes.iterator();
		while(it.hasNext()){
			Double next = it.next();
			double tempbytes = next.doubleValue();
			double result = tempbytes*1e-6;
			megabytes.add(new Double(result));
		}
		
		return megabytes;
	}

	@Override
	public double Summary() {
		double bytes = GetEndInfo("bytes");
		return bytes*1e-6;
	}

	@Override
	public String measure() {
		return "MB";
	}

}
