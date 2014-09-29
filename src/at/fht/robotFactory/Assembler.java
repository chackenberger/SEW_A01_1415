package at.fht.robotFactory;

import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import at.hackenberger.lib.Watchable;

public class Assembler extends Employee implements Runnable, Watchable {
	
	private Part[] storage;
	
	private Logger logger;
	private boolean shutdown = false;
	
	public Assembler() {
		this.setID(Factory.getOffice().requestID());
		logger = LogManager.getLogger(this.getClass().getName() + "(" + this.getID()  + ")");
		this.storage = new Part[PartType.values().length];
	}
	
	/**
	 * Requests a PartType from the StorageGuy
	 * @param type
	 * @return 
	 */
	private Part requestPart(PartType type) {
		logger.info("Requesting for Part");
		return Factory.getStorage().getPart(type);
	}
	/**
	 * returns Parts back to the Storageguy if the 
	 * Assembler didnt get all requierd Parts
	 */
	private void returnParts(){
		logger.error("Something went wrong while requesting for Parts");
		Storageguy sg = Factory.getStorage();
		for (int i = 0; i < storage.length; i++){
			sg.storePart(storage[i]);
			storage[i] = null;
		}
	}
	/**
	 * sorts numbers as a symbol of assembling
	 * @param type
	 */
	private void sort(Part part) {
		logger.info("assembles");
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
		logger.info("archives the Threadee");
		Factory.getStorage().storeThreadee(this.getID(), parts);
	}
	/**
	 * 
	 */
	@Override
	public void run() {
		while(!shutdown) {
			PartType[] types = PartType.values();
			for(int i = 0; i < types.length; i++) {
				Part part = requestPart(types[i]);
				if(part == null) {
					returnParts();
					continue;
				}
				sort(part);
				storage[i] = part;
			}
			robotArchive(storage);
		}
	}
	/**
	 * for the Watchdog
	 */
	@Override
	public boolean shutdown() {
		return (this.shutdown = true);
	}
}
