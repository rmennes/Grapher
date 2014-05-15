package Grapher;

import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Vector;

import net.minidev.json.parser.ParseException;

public class Speed extends Parser {

	public Speed(String file) throws FileNotFoundException, ParseException {
		super(file);

	}

	@Override
	public Vector<Double> Get() {
		Vector<Double> bytes = GetIntervalInfo("bits_per_second");
		Vector<Double> megabits = new Vector<Double>(bytes.size());
		Iterator<Double> it = bytes.iterator();
		while(it.hasNext()){
			Double next = it.next();
			double tempbits = next.doubleValue();
			double result = tempbits*1e-6;
			megabits.add(new Double(result));
		}
		
		return megabits;
	}

	@Override
	public double Summary() {
		double bytes = GetEndInfo("bits_per_second");
		return bytes*1e-6;
	}

	@Override
	public String measure() {
		return "Mb/sec";
	}

}
