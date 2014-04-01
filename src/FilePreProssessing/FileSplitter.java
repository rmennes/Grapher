package FilePreProssessing;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Stack;

public class FileSplitter {

	
	public static void SplitFile(String filename, String dir) throws IOException{
		String splitName = "split_";
		int open = 0;
		int file = 0;
		FileInputStream input = new FileInputStream(new File(filename));
		FileOutputStream output = new FileOutputStream(new File(dir + "/" + splitName + Integer.toString(file) + ".json"));
		int content;
		while((content = input.read()) != -1){
			char tempChar = (char)content;
			if(tempChar == '{'){
				open++;
			}
			else if(tempChar == '}'){
				open--;
			}else if(open == 0){
				continue;
			}
			output.write(content);
			if(open == 0){
				output.write((int)'\n');
				output.close();
				file++;
				output = new FileOutputStream(new File(dir + "/" + splitName + Integer.toString(file) + ".json"));
			}
		}
		input.close();
		output.close();
		File last = new File(dir + "/" + splitName + Integer.toString(file) + ".json");
		last.delete();
	}
	
	public static void main(String[] args) {
		try{
			SplitFile(args[0], args[1]);
		}catch(Exception e){
			e.printStackTrace();
		}

	}

}
