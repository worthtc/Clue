package clueGame;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class BadConfigFormatException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L; // Gets rid of warnings!
	int columns;
	String message;
	public BadConfigFormatException () {
		super();
		try{
			//Write the Error that occurred to a log file 
			this.message = "BadConfigFormatException";
			File file = new File("logfile.txt");
			FileWriter file_write = new FileWriter(file);
			file_write.write(toString());
			file_write.close();
		}catch (IOException e ){
			System.out.println( "Error opening logfile.txt occured in BadConfigFormatException");
		}
	}
    
	
	public BadConfigFormatException (String err){
		super();
		try{
			//Write the Error that occurred to a log file 
			this.message = err;
			File file = new File("logfile.txt");
			FileWriter file_write = new FileWriter(file);
			file_write.write(toString());
			file_write.close();
		}catch ( IOException e ){
			System.out.println( "Error opening logfile.txt occured in BadConfigFormatException");
		}
	}
	@Override
	public String toString() {
		return "Error: " + message;
	}
	
	

}
