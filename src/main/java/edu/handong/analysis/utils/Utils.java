package edu.handong.analysis.utils;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.PrintWriter;

public class Utils {
	
	static ArrayList<String> getLines(String file, boolean removeHeader){	
		ArrayList<String> temp;
		temp = null;
		Scanner inputStream = null;
		try {
			inputStream = new Scanner(new File(file));
		}  catch (FileNotFoundException e) {
			System.out.println ("Error opening the file " + file);
			System.exit (0);
		}
		while (inputStream.hasNextLine ()) {
			String line = inputStream.nextLine ();
			if(removeHeader == true){
				if(line.split(",")[1].trim() == "StudentID") {
					continue;
				}else {
					temp.add(line);				
				}				
			}else {
				temp.add(line);
			}
		}
		inputStream.close ();
		return temp;
	}
	static void writeAFile(ArrayList<String> lines, String targetFileName) {
		PrintWriter outputStream = null;
		try {
			outputStream = new PrintWriter(targetFileName);
		} catch(FileNotFoundException e) {
			System.out.println("Error opening the file " + targetFileName);
			System.exit(0);
		}
        for (String line : lines) {
            outputStream.println (line);
        }
        outputStream.close();
	}
}
