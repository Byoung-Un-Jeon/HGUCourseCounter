package edu.handong.analysise.utils;
import java.util.ArrayList;
import java.util.Iterator;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import java.io.PrintWriter;
import java.io.Reader;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class Utils {
	
	public static ArrayList<CSVRecord> getLines(String file, boolean removeHeader) {	
		
		//리턴값
		ArrayList<CSVRecord> temp = new ArrayList<CSVRecord>();
		FileReader fileReader = null;
		CSVParser csvFileParser = null;
		CSVFormat csvFileFormat = CSVFormat.EXCEL
				.withIgnoreEmptyLines(true)
				.withTrim();
		try {
			 fileReader = new FileReader(file);	
			 csvFileParser = csvFileFormat.parse(fileReader);
			 }catch(Exception e) {
			System.out.println("The file path does not exist. Please check your CLI argument!");
		}
		//템프에 넣어주기 위해서 이터레이터로 만들어줌
		Iterator<CSVRecord> iter = csvFileParser.iterator();
		//템프에 값 넣어줌
		try {
			while(iter.hasNext()) {
				temp.add(iter.next());
			}
		}catch(Exception e){
			System.out.println("iter.hasNext() exception");
		}
/*		catch (FileNotFoundException e) {
			System.out.println ("Error opening the file " + file);
			System.exit (0);
		}catch(NotEnoughArgumentException e) {
			System.out.println(e);
		}*/
		if(removeHeader == true)
			temp.remove(0);
		try {
			fileReader.close();			
		}catch(Exception e){
			
		}
		
		return temp;
	}
	
	public static void writeAFile(ArrayList<String> lines, String targetFileName) {
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
