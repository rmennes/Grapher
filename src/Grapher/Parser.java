package Grapher;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Vector;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

public abstract class Parser {

	JSONObject m_data;
	
	public Parser(String file) throws FileNotFoundException, ParseException{
		JSONParser parser = new JSONParser(JSONParser.ACCEPT_SIMPLE_QUOTE | JSONParser.ACCEPT_LEADING_ZERO);
		m_data = (JSONObject) parser.parse(new FileInputStream(new File(file)));
	}
	
	public String Error(){
		String output = (String) m_data.get("error");
		return output;
	}
	
	public abstract Vector<Double> Get();
	
	public abstract double Summary();
	
	public abstract String measure();
	
	public int GetSize(){
		JSONObject start = (JSONObject)m_data.get("start");
		JSONObject testStart = (JSONObject)start.get("test_start");
		Number blksize = (Number)testStart.get("blksize");
		return blksize.intValue();
	}
	
	public Vector<Double> GetTimes(){
		return GetIntervalInfo("end");
	}

	protected Vector<Double> GetIntervalInfo(String value){
		Vector<Double> result = new Vector<Double>();
		JSONArray intervals = (JSONArray)m_data.get("intervals");
		Iterator<Object> it = intervals.iterator();
		while(it.hasNext()){
			JSONObject next = (JSONObject) it.next();
			JSONArray streamArray = (JSONArray) next.get("streams");
			JSONObject streamObject = (JSONObject) streamArray.get(0);
			Number start = (Number) streamObject.get(value);
			Double d = new Double(start.doubleValue());
			result.add(d);
		}
		
		return result;
	}
	
	protected double GetEndInfo(String value){
		JSONArray intervals = (JSONArray)m_data.get("end");
		Iterator<Object> it = intervals.iterator();
		while(it.hasNext()){
			JSONObject next = (JSONObject) it.next();
			JSONArray streamArray = (JSONArray) next.get("streams");
			JSONObject streamObject = (JSONObject) streamArray.get(0);
			Number start = (Number) streamObject.get(value);
			return start.doubleValue();
		}
		
		return 0;
	}

}
