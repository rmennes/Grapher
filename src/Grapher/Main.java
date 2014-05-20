package Grapher;

import java.awt.List;
import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;


public class Main {

	public static void makeLinePNG(String[] files, String value, String title, String file) throws Exception{
		ArrayList<Vector<Double>> results = new ArrayList<Vector<Double>>();
		Grapher g = new Grapher(title);
		String yName = "";
		boolean first = true;
		System.out.println("=== Start parsing files ===");
		for(int i = 0; i < files.length; i++){
			Parser p = ParserFactory.getParser(value, files[i]);
			if (p == null)
				System.err.println("Parser was null.. wrong type?");
			if(first){
				System.out.println("Parse timings");
				Vector<Double> points = p.GetTimes();
				points.setSize(10);
				g.SetXData(points);
				yName = p.measure();
				first = false;
			}
			//System.out.println("Parse: " + files[i]);
			String error = p.Error();
			if(error != null){
				System.err.println("Error detected in file: " + files[i] + "\n\t" + error);
				continue;
			}
			Vector<Double> tempResults = p.Get();
			results.add(tempResults);
		}
		System.out.println("=== Start calculator ===");
		System.out.println("Calculate average");
		Vector<Double> aver = Calculator.Average(results);
		aver.setSize(10);
		System.out.println("Calculate max");
		Vector<Double> max = Calculator.Max(results);
		max.setSize(10);
		System.out.println("Calculate min");
		Vector<Double> min = Calculator.Min(results);
		min.setSize(10);
		System.out.println("=== Generate Image ===");
		System.out.println("Add max");
		g.addSet("Max", max);
		System.out.println("Add min");
		g.addSet("Min", min);
		System.out.println("Add average");
		g.addSet("Average", aver);
		System.out.println("Print Chart");
		String url = g.getLineChartURL(625, 470, "s", yName);
		System.out.println("=== Generated URL: " + url);
		PrintWriter writer = new PrintWriter(file, "UTF-8");
		writer.println(url);
		writer.close();
	}
	
	public static void makeLinePNG(String[][] files, String[] testCase, String value, String title, String file) throws Exception{
		ArrayList<TreeMap<Integer, ArrayList<Vector<Double>>>> results =
				new ArrayList<TreeMap<Integer, ArrayList<Vector<Double>>>>();
		Grapher g = new Grapher(title);
		String yName = "";
		
		//PARSE INPUT FILES
		System.out.println("=== Start parsing files ===");
		for(int i = 0; i < files.length; i++){
			TreeMap<Integer, ArrayList<Vector<Double>>> oneDir = new TreeMap<Integer, ArrayList<Vector<Double>>>();
			for(int j = 0; j < files[i].length; j++){
				System.out.println("Parse: " + files[i][j]);
				Parser p = ParserFactory.getParser(value, files[i][j]);
				yName = p.measure();
				Vector<Double> tempResults = p.Get();
				Integer blksize = new Integer(p.GetSize());
				if(oneDir.containsKey(blksize)){
					oneDir.get(blksize).add(tempResults);
				}else{
					ArrayList<Vector<Double>> n = new ArrayList<Vector<Double>>();
					n.add(tempResults);
					oneDir.put(blksize, n);
				}
			}
			results.add(oneDir);
		}
		
		//SET X DATA
		Set<Integer> blksize = results.get(0).keySet();
		ArrayList<Double> blksizes = new ArrayList<Double>();
		Iterator<Integer> itTemp = blksize.iterator();
		while(itTemp.hasNext()){
			blksizes.add(new Double(itTemp.next().doubleValue()));
		}
		Collections.sort(blksizes);
		
		g.SetXData(blksizes);
		
		//CALCULATE AVG
		System.out.println("=== Start calculator ===");
		Iterator<TreeMap<Integer, ArrayList<Vector<Double>>>> it = results.iterator();
		int i = 0;
		while(it.hasNext()){
			TreeMap<Integer, ArrayList<Vector<Double>>> tempArray = it.next();
			Iterator<Entry<Integer, ArrayList<Vector<Double>>>> it2 = tempArray.entrySet().iterator();
			HashMap<Integer, Vector<Double>> newCalculated = new HashMap<Integer, Vector<Double>>();
			while(it2.hasNext()){
				Entry<Integer, ArrayList<Vector<Double>>> next = it2.next();
				Vector<Double> tempResult = Calculator.Average(next.getValue());
				newCalculated.put(next.getKey(), tempResult);
			}
			Vector<Double> newResults = Calculator.OtherAverage(newCalculated.values());
			g.addSet(testCase[i], newResults);
			i++;
		}
		
		System.out.println("=== Generate Image ===");
		String url = g.getLineChartURL(625, 470, "b", yName);
		System.out.println("=== Generated URL: " + url);
		PrintWriter writer = new PrintWriter(file, "UTF-8");
		writer.println(url);
		writer.close();
		
		
//		ArrayList<HashMap<Integer, ArrayList<Vector<Double>>>> results = new ArrayList<HashMap<Integer,ArrayList<Vector<Double>>>>();
//		//TEST				SIZE	FILE		RESULTS PER SEC
//		Grapher g = new Grapher(title);
//		String yName = "";
//		boolean first = true;
//		System.out.println("=== Start parsing files ===");
//		for(int i = 0; i < files.length; i++){
//			ArrayList<Vector<Double>> tempArray = new  ArrayList<Vector<Double>>();
//			for(int j = 0; j < files[i].length; j++){
//				System.out.println("Parse: " + files[i]);
//				Parser p = ParserFactory.getParser(value, files[i][j]);
//				Vector<Double> tempResults = p.Get();
//				int blksize = p.GetSize();
//				if(first){
//					g.SetXData(p.GetTimes());
//					yName = p.measure();
//					first = false;
//				}
//				if()
//			}
//			results.add(tempArray);
//		}
//		System.out.println("=== Start calculator ===");
//		Iterator<HashMap<Integer, ArrayList<Vector<Double>>>> it = results.iterator();
//		int i = 0;
//		while(it.hasNext()){
//			HashMap<Integer, Vector<Double>> tempArray = it.next();
//			int percent = i / results.size();
//			System.out.println("Calculate average (" + Integer.toString(percent) + "%)");
//			Vector<Double> aver = Calculator.Average(tempArray);
//			g.addSet("Data " + Integer.toString(i), aver);
//			i++;
//		}
//		System.out.println("=== Generate Image ===");
//		String url = g.getLineChartURL(625, 470, "s", yName);
//		System.out.println("=== Generated URL: " + url);
	}

	public static void main(String[] args) {
		try{
			if(args.length < 4){
				System.out.println("Use follow arguments <type> <outputfile> <title> <inputmap1> [<inputmap2> ...]");
				return;
			}
			String value = args[0];
			String file = args[1];
			String title = args[2];
			if(args.length == 4){
				File dir = new File(args[3]);
				File[] listOfFiles = dir.listFiles(new FileFilter() {
					
					@Override
					public boolean accept(File pathname) {
						String name = pathname.getName();
						return pathname.exists() && pathname.canRead() && pathname.isFile() && name.substring(name.length()-5).equals(".json");
					}
				});
				String files[] = new String[listOfFiles.length];
				for(int i = 0; i < listOfFiles.length; i++){
					files[i] = listOfFiles[i].getAbsolutePath();
				}
				
				makeLinePNG(files, value, title, file);
			}else{
				int length = args.length - 3;
				String [][] files = new String[length][];
				String [] testCase = new String[length]; 
				for(int i = 0; i < length; i++){
					File dir = new File(args[3+i]);
					testCase[i] = dir.getName();
					File[] listOfFiles = dir.listFiles(new FileFilter() {
						
						@Override
						public boolean accept(File pathname) {
							String name = pathname.getName();
							return pathname.exists() && pathname.canRead() && pathname.isFile() && name.substring(name.length()-5).equals(".json");
						}
					});
					String lfiles[] = new String[listOfFiles.length];
					for(int j = 0; j < listOfFiles.length; j++){
						lfiles[j] = listOfFiles[j].getAbsolutePath();
					}
					files[i] = lfiles;
				}
				makeLinePNG(files, testCase, value, title, file);
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
