package FilePreProssessing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

public class PreProssessing {
	
	public static void PreprocessFile(File f) throws IOException
	{
		File temp = new File("temp");
		FileOutputStream ostream = new FileOutputStream(temp);
		BufferedReader breader = new BufferedReader(new FileReader(f));
		while(breader.ready()){
			String in = breader.readLine();
			String out = checkLine(in);
			for(int i = 0; i < out.length(); ++i){
				char it = out.charAt(i);
				ostream.write((int)it);
			}
			ostream.write((int)'\n');
		}
		ostream.close();
		breader.close();
		f.delete();
		temp.renameTo(f);
	}
	
	public static String checkLine(String input){
		String output = new String("");
		int i = input.indexOf("-nan");
		if(i == -1)return input;
		output = output.concat(input.substring(0, i));
		output = output.concat("0");
		output = output.concat(input.substring(i+4));
		return output;
	}

	public static void main(String[] args) {
		try{
			String folderName = args[0];
			File folder = new File(folderName);
			File [] files = folder.listFiles();
			for(int i = 0; i < files.length; ++i){
				PreprocessFile(files[i]);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
