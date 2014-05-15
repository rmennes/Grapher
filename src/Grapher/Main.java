package Grapher;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
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
	
	public static void makeLinePNG(String[][] files, String value, String title, String file) throws Exception{
		ArrayList<ArrayList<Vector<Double>>> results = new ArrayList<ArrayList<Vector<Double>>>();
		Grapher g = new Grapher(title);
		String yName = "";
		boolean first = true;
		System.out.println("=== Start parsing files ===");
		for(int i = 0; i < files.length; i++){
			ArrayList<Vector<Double>> tempArray = new ArrayList<Vector<Double>>();
			for(int j = 0; j < files[i].length; j++){
				System.out.println("Parse: " + files[i]);
				Parser p = ParserFactory.getParser(value, files[i][j]);
				Vector<Double> tempResults = p.Get();
				if(first){
					g.SetXData(p.GetTimes());
					yName = p.measure();
					first = false;
				}
				tempArray.add(tempResults);
			}
			results.add(tempArray);
		}
		System.out.println("=== Start calculator ===");
		Iterator<ArrayList<Vector<Double>>> it = results.iterator();
		int i = 0;
		while(it.hasNext()){
			ArrayList<Vector<Double>> tempArray = it.next();
			int percent = i / results.size();
			System.out.println("Calculate average (" + Integer.toString(percent) + "%)");
			Vector<Double> aver = Calculator.Average(tempArray);
			g.addSet("Data " + Integer.toString(i), aver);
			i++;
		}
		System.out.println("=== Generate Image ===");
		String url = g.getLineChartURL(625, 470, "s", yName);
		System.out.println("=== Generated URL: " + url);
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
				for(int i = 0; i < length; i++){
					File dir = new File(args[3+i]);
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
				makeLinePNG(files, value, title, file);
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
