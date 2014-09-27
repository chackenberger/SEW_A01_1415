package at.fht.robotFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import at.hackenberger.lib.Watchable;

public class Assembler implements Runnable, Watchable {
	
	private Part[] storage;
	
	private Logger logger;
	
	public Assembler() {
		
	}
	
	/**
	 * Requests a PartType from the StorageGuy
	 * @param type
	 * @return 
	 */
	private Part requestPart(PartType type) {
		return null;
	}
	/**
	 * returns Parts back to the StorageGuy if the 
	 * Assembler didnt got all requierd Parts
	 */
	private void returnParts(){
		
	}
	/**
	 * sorts numbers as a symbol of assembling
	 * @param type
	 */
	private void sort(Part part) {
		
	}

	private void robotArchive(Part[] parts){
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean shutdown() {
		// TODO Auto-generated method stub
		return false;
	}
}
