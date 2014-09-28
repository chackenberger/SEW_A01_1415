package at.fht.robotFactory;

import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import at.hackenberger.lib.Watchable;

public class Assembler extends Employee implements Runnable, Watchable {
	
	private Part[] storage;
	
	private Logger logger;
	private Storageguy sg;
	
	public Assembler() {
		this.sg = new Storageguy(null);
	}
	
	/**
	 * Requests a PartType from the StorageGuy
	 * @param type
	 * @return 
	 */
	private Part requestPart(PartType type) {
		return this.sg.getPart(type);
	}
	/**
	 * returns Parts back to the Storageguy if the 
	 * Assembler didnt get all requierd Parts
	 */
	private void returnParts(){
		for (int i = 0; i < storage.length; i++){
			this.sg.storePart(storage[i]);
			storage[i] = null;
		}
	}
	/**
	 * sorts numbers as a symbol of assembling
	 * @param type
	 */
	public void sort(Part part) {
//		logger = LogManager.getLogger(this.getClass().getName());
		int[] sortHelp;
		sortHelp = part.getNumbers();
		Arrays.sort(sortHelp);
		part.setNumbers(sortHelp);
	}
	/**
	 * puts all the Robotparts together to assemble a Threadee
	 * @param parts
	 */
	private void robotArchive(Part[] parts){
		
	}
	/**
	 * 
	 */
	@Override
	public void run() {
		this.storage[0] = sg.getPart(PartType.ARM);
		this.storage[1] = sg.getPart(PartType.EYE);
		this.storage[2] = sg.getPart(PartType.GEAR);
		this.storage[3] = sg.getPart(PartType.BODY);
		for (int i = 0; i < storage.length; i++){
			if (this.storage[i].equals(null)) {
				returnParts();
				break;
			}
		}
		for (int i = 0; i < storage.length; i++) {
			sort(this.storage[i]);
		}
		robotArchive(storage);
	}
	/**
	 * for the Watchdog
	 */
	@Override
	public boolean shutdown() {
		return false;
	}
}
