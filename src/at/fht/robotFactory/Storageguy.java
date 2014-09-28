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
 * Handler for reading and writing Parts and
 * Threadees to the hard disk
 */
public class Storageguy extends Employee {

	private Logger logger;

	private HashMap<PartType, File> files;

	private File productFile;

	private final String PRODUCT_FILE_PATH = "products.csv";

	/**
	 * Created a new Storageguy object and creates the files and directories,
	 * for the storage on the hard disk
	 * 
	 * @param storageDir the directory where the files will be stored
	 */
	public Storageguy(String storageDir) {
		this.setID(Factory.getOffice().requestID());
		logger = LogManager.getLogger(this.getClass().getName() + "("
				+ this.getID() + ")");
		files = new HashMap<PartType, File>();
		for (PartType type : PartType.values()) {
			File file = new File(storageDir, type.getFileName());
			file.getParentFile().mkdirs();
			try {
				file.createNewFile();
			} catch (IOException ex) {
				logger.error("Could not create " + file.getAbsolutePath()
						+ ": " + ex.getMessage());
				logger.info("Stopping Program!");
				System.exit(0);
			}
			files.put(type, file);
		}
		File file = new File(storageDir, PRODUCT_FILE_PATH);
		file.getParentFile().mkdirs();
		try {
			file.createNewFile();
		} catch (IOException ex) {
			logger.error("Could not create " + file.getAbsolutePath() + ": "
					+ ex.getMessage());
			logger.info("Stopping Program!");
			System.exit(0);
		}
		productFile = file;
	}

	/**
	 * Reads a Part from the Hard Disk and returns it
	 * 
	 * @param type the type of the Part which should be returned
	 * @return the read Part
	 */
	public Part getPart(PartType type) {
		if (type == null)
			return null;
		synchronized (type) {
			File file = files.get(type);
			try (CSVReader reader = new CSVReader(new FileReader(file))) { // try-with-resources
																			// statement
				List<String[]> lines;
				try {
					lines = reader.readAll();
				} catch (IOException ex) {
					logger.error("Could not read from " + type.getFileName()
							+ ": " + ex.getMessage());
					return null;
				}
				String[] fields;
				int[] numbers;
				while (true) {
					if (lines.isEmpty())
						return null;
					fields = lines.remove(lines.size() - 1);
					numbers = new int[fields.length - 1];
					try {
						for (int i = 1; i < fields.length; i++) {
							numbers[i - 1] = Integer.parseInt(fields[i]);
						}
					} catch (NumberFormatException ex) {
						logger.error("Could not convert a line from "
								+ type.getFileName()
								+ " to a Part! Try next Line");
						continue;
					}
					break;
				}
				try (CSVWriter writer = new CSVWriter(new FileWriter(file,
						false))) {
					writer.writeAll(lines);
					try {
						writer.flush();
					} catch (IOException ex) {
						logger.error("Could not write changes to "
								+ type.getFileName() + ": " + ex.getMessage());
						return null;
					}
				} catch (IOException ex) {
					logger.error("Something went wrong while creating writer for "
							+ file.getAbsolutePath() + ": " + ex.getMessage());
					return null;
				}
				return new Part(type, numbers);
			} catch (IOException ex) {
				logger.error("Something went wrong while creating reader for "
						+ file.getAbsolutePath() + ": " + ex.getMessage());
				return null;
			}
		}
	}

	/**
	 * Writes a Part to the hard disk
	 * 
	 * @param part the Part which should be written to the hard disk
	 */
	public void storePart(Part part) {
		if (part == null)
			return;
		PartType type = part.getPartType();
		if (type == null)
			return;
		synchronized (type) {
			File file = files.get(part.getPartType());
			try (CSVWriter writer = new CSVWriter(new FileWriter(file, true))) {
				String[] newLine = new String[part.getNumbers().length + 1];
				newLine[0] = type.toString();
				for (int i = 1; i < newLine.length; i++) {
					newLine[i] = "" + part.getNumbers()[i - 1];
				}
				writer.writeNext(newLine);
				try {
					writer.flush();
				} catch (IOException ex) {
					logger.error("Could not write changes to "
							+ type.getFileName() + ": " + ex.getMessage());
					return;
				}
			} catch (IOException ex) {
				logger.error("Something went wrong while creating writer for "
						+ file.getAbsolutePath() + ": " + ex.getMessage());
			}
		}
	}

	/**
	 * Writes a Threadee to the hard disk
	 * 
	 * @param employeeID the id from the employee which assembled the Threadee
	 * @param parts the parts of which the Threadee consists
	 */
	public void storeThreadee(long employeeID, Part[] parts) {
		if (parts == null)
			return;
		ArrayList<String> line = new ArrayList<String>();
		line.add("Threadee-ID" + Factory.getOffice().requestRobotID());
		line.add("Employee-ID" + employeeID);
		for (Part part : parts) {
			if (part == null)
				continue;
			line.add("" + part.getPartType().toString());
			for (int number : part.getNumbers())
				line.add("" + number);
		}
		String[] newLine = line.toArray(new String[line.size()]);
		synchronized (productFile) {
			try (CSVWriter writer = new CSVWriter(new FileWriter(productFile,
					true))) {
				writer.writeNext(newLine);
				try {
					writer.flush();
				} catch (IOException ex) {
					logger.error("Could not write changes to "
							+ productFile.getAbsolutePath() + ": "
							+ ex.getMessage());
					return;
				}
			} catch (IOException ex) {
				logger.error("Something went wrong while creating writer for "
						+ productFile.getAbsolutePath() + ": "
						+ ex.getMessage());
			}
		}
	}
}
