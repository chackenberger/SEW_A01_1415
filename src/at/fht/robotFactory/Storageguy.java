package at.fht.robotFactory;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

/**
 * 
 * @author Christoph Hackenberger
 * Handler for reading and writing Parts and Threadees to the hard disk
 */
public class Storageguy extends Employee {

	private Logger logger;
	
	private HashMap<PartType, CSVReader> readers;
	private HashMap<PartType, CSVWriter> writers;
	
	private CSVWriter threadeeWriter;
	
	private final String PRODUCT_FILE = "products.csv";
	
	/**
	 * Created a new Storageguy object and creates the files and directories,
	 * for the storage on the hard disk
	 * @param storageDir the directory where the files will be stored
	 */
	public Storageguy(String storageDir) {
		logger = LogManager.getLogger(this.getClass().getName() + "(" + Factory.getOffice().requestID() + ")");
		for(PartType type : PartType.values()) {
			File file = new File(storageDir, type.getFileName());
			file.mkdirs();
			try {
				file.createNewFile();
			}catch (IOException ex) {
				logger.error("Could not create " + file.getAbsolutePath() + ": " + ex.getMessage());
				logger.info("Stopping Program!");
				System.exit(0);
			}
			try {
				readers.put(type, new CSVReader(new FileReader(file)));
				writers.put(type, new CSVWriter(new FileWriter(file, false))); //the second argument defines is the writer should append or not
			} catch (IOException ex) {
				logger.error("Something went wrong while creating readers and writers for " + file.getAbsolutePath() +
						": " + ex.getMessage());
				logger.info("Stopping Program!");
				System.exit(0);
			}
		}
		File file = new File(storageDir, PRODUCT_FILE);
		file.mkdirs();
		try {
			file.createNewFile();
		} catch (IOException ex) {
			logger.error("Could not create " + file.getAbsolutePath() + ": " + ex.getMessage());
			logger.info("Stopping Program!");
			System.exit(0);
		}
		try {
			threadeeWriter = new CSVWriter(new FileWriter(file,true)); //the second argument defines is the writer should append or not
		} catch (IOException ex) {
			logger.error("Something went wrong while creating readers and writers for " + file.getAbsolutePath() +
					": " + ex.getMessage());
			logger.info("Stopping Program!");
			System.exit(0);
		}
	}
	
	/**
	 * Reads a Part from the Hard Disk and returns it
	 * @param type the type of the Part which should be returned
	 * @return the read Part
	 */
	public Part getPart(PartType type) {
		synchronized (type) {
			CSVReader reader = readers.get(type);
			CSVWriter writer = writers.get(type);
			List<String[]> lines;
			try {
				lines = reader.readAll();
			} catch (IOException ex) {
				logger.error("Could not read from " + type.getFileName() + ": " + ex.getMessage());
				return null;
			}
			while(true) {
				if(lines.isEmpty())
					return null;
				String[] fields = lines.remove(lines.size()-1);
				int[] numbers = new int[fields.length-1];
				try {
					for(int i = 1; i < fields.length; i++) {
						numbers[i-1] = Integer.parseInt(fields[i]);
					}
					writer.writeAll(lines);
					try {
						writer.flush();
					} catch (IOException ex) {
						logger.error("Could not write changes to " + type.getFileName() + ": " + ex.getMessage());
						return null;
					}
					return new Part(type, numbers);
				}catch (NumberFormatException ex) {
					logger.error("Could not convert a line from " + type.getFileName() + " to a Part! Try next Line");
				}
			}
		}
	}
	
	/**
	 * Writes a Part to the hard disk
	 * @param part the Part which should be written to the hard disk
	 */
	public void storePart(Part part) {
		PartType type = part.getPartType();
		synchronized (type) {
			CSVReader reader = readers.get(type);
			CSVWriter writer = writers.get(type);
			List<String[]> lines;
			try {
				lines = reader.readAll();
			} catch (IOException ex) {
				logger.error("Could not read from " + type.getFileName() + ": " + ex.getMessage());
				return;
			}
			String[] newLine = new String[part.getNumbers().length + 1];
			newLine[0] = type.toString();
			for(int i = 1; i < newLine.length; i++) {
				newLine[i] = "" + part.getNumbers()[i-1];
			}
			lines.add(newLine);
			writer.writeAll(lines);
			try {
				writer.flush();
			} catch (IOException ex) {
				logger.error("Could not write changes to " + type.getFileName() + ": " + ex.getMessage());
				return;
			}
		}
	}
	
	/**
	 * Writes a Threadee to the hard disk
	 * @param employeeID the id from the employee which assembled the Threadee
	 * @param parts the parts of which the Threadee consists
	 */
	public void storeThreadee(long employeeID, Part[] parts) {
		ArrayList<String> line = new ArrayList<String>();
		line.add("Threadee-ID" + Factory.getOffice().requestRobotID() + 
				",Employee-ID" + employeeID);
		for(Part part : parts) {
			line.add(","+part.getPartType().toString());
			for(int number : part.getNumbers())
				line.add(","+number);
		}
		String[] newLine = line.toArray(new String[line.size()]);
		synchronized (threadeeWriter) {
			threadeeWriter.writeNext(newLine);
			try {
				threadeeWriter.flush();
			} catch (IOException ex) {
				logger.error("Could not write changes to " + PRODUCT_FILE + ": " + ex.getMessage());
				return;
			}
		}
	}
}
