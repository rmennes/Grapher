package Grapher;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;


public class Main {

	public static void makeLinePNG(String[] files, String value, String title, String file) throws Exception{
		ArrayList<Vector<Double>> results = new ArrayList<Vector<Double>>();
		Grapher g = new Grapher(title);
		String yName = "";
		boolean first = true;
		for(int i = 0; i < files.length; i++){
			Parser p = ParserFactory.getParser(value, files[i]);
			Vector<Double> tempResults = p.Get();
			if(first){
				g.SetXData(p.GetTimes());
				yName = p.measure();
				first = false;
			}
			results.add(tempResults);
		}
		Vector<Double> aver = Calculator.Average(results);
		Vector<Double> max = Calculator.Max(results);
		Vector<Double> min = Calculator.Min(results);
		g.addSet("Average", aver);
		g.addSet("Max", max);
		g.addSet("Min", min);
		g.printLineChart(file, 640, 480, "s", yName);
	}

	public static void main(String[] args) {
		try{
			String value = args[0];
			String file = args[1];
			String title = args[2];
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
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
