package at.fht.robotFactory;

import org.apache.logging.log4j.Logger;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

public class Storageguy extends Employee {

	private Logger logger;
	
	private CSVReader[] readers;
	private CSVWriter[] writers;
	
	public Storageguy(String storageDir) {
		
	}
	
	public Part getPart(PartType type) {
		return null;
	}
	
	public void storePart(Part part) {
		
	}
}
