package clueGame;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class BadConfigFormatException extends Exception {
	//int columns;
	String message;
	public BadConfigFormatException () {}
    
	/*public BadConfigFormatException (int columns) throws Exception{
	super();
	this.columns = columns;
	 File file = new File("logfile.txt");
	 FileWriter file_write = new FileWriter(file);
	 file_write.write( toString());
	 file_write.close();
	}*/
	public BadConfigFormatException (String room_initial) throws Exception{
		super();
		this.message = room_initial;
		File file = new File("logfile.txt");
		FileWriter file_write = new FileWriter(file);
		file_write.write(toString());
		file_write.close();
	}
	@Override
	public String toString() {
		return "Error " + message + " occured";
	}
	
	

}
