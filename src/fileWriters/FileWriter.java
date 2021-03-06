package fileWriters;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import wrappers.SystemResource;

public class FileWriter implements SystemResource {
	File file;
	OutputStream out;
	boolean enabled = true;

	public FileWriter(String path) {
		try {
			file = new File(path);
			out = new FileOutputStream(file);
			System.out.println("Sucessfully opened an output stream to " + path);
		} catch (FileNotFoundException e) {
			createNewFile();
		}
	}

	public void createNewFile() {
		System.out.println("File not found, creating file...");
		// Attempt to make the file three times
		boolean successful = false;
		for (int retry = 1; retry <= 3; retry++) {
			try {
				this.file.createNewFile();
				successful = true;
				break;
			} catch (IOException e1) {
				successful = false;
				System.out.println("Could not make file... Trying again (" + retry + "/3)");
			}
		}
		if (successful) {
			System.out.println("Successfully made file at " + file.getAbsolutePath());
		} else {
			System.out.println("Could not make file at " + file.getAbsolutePath());
			System.out.println("FileWriter will not write to file as it could not be created.");
			enabled = false;
		}
	}

	public void write(byte[] data) {
		if (enabled) {
			try {
				out.write(data, 0, data.length);
			} catch (IOException e) {
				System.out.println("Could not write " + data.length + " bytes to file");
			}
		}
	}
	
	@Override
	public void close() {
		try {
			out.close();
		} catch (IOException e) {
			System.out.println("Could not close stream");
		}
		System.out.println("Closed output stream");
	}
	
	@Override
	public void open() {
		if (!file.exists()) {
			createNewFile();
		}
	}

	@Override
	public void enable() {
		enabled = true;
		
	}

	@Override
	public void disable() {
		enabled = false;
	}
}
