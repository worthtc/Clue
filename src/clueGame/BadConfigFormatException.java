package clueGame;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class BadConfigFormatException extends Exception {
	int columns;
	Character room_initial;
	public BadConfigFormatException () {}
    
	public BadConfigFormatException (int columns) throws Exception{
	super();
	this.columns = columns;
	 File file = new File("logfile.txt");
	 FileWriter file_write = new FileWriter(file);
	 file_write.write( toString());
	 file_write.close();
	}
	public BadConfigFormatException (Character room_initial) throws Exception{
		super();
		this.room_initial = room_initial;
		File file = new File("logfile.txt");
		FileWriter file_write = new FileWriter(file);
		file_write.write("Room initial " + room_initial + " is wrong");
	}
	@Override
	public String toString() {
		return "BadConfigFormatException [columns=" + columns + "]";
	}
	
	

}
