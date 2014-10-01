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
		logger = LogManager.getLogger(this.getClass().getName() + "(" + this.getID()  + ")");
		this.storage = new Part[PartType.values().length + 2]; //+2 because eye and arm are needed twice
		
	}
	
	/**
	 * Requests a PartType from the StorageGuy
	 * @param type
	 * @return 
	 */
	public Part requestPart(PartType type) {
		logger.info("request " + type + " from storage");
		return Factory.getStorage().getPart(type);
	}
	/**
	 * returns Parts back to the Storageguy if the 
	 * Assembler didnt get all requierd Parts
	 */
	private void returnParts(){
		logger.error("Something went wrong while requesting for Parts");
		logger.warn("could not get all parts for a Threade return current parts to storage");
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
	public void sort(Part part) {
		logger.info("sorting Part " + part.getPartType());
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
		logger.info("send new Threadee to storage");
		Factory.getStorage().storeThreadee(this.getID(), parts);
	}
	/**
	 * 
	 */
	@Override
	public void run() {
		while(!shutdown) {
			PartType[] types = new PartType[storage.length];
			int j = 0;
			for(PartType type : PartType.values()) {
				types[j] = type;
				j++;
				if(type == PartType.ARM || type == PartType.EYE) {
					types[j] = type;
					j++;
				}
			}
			boolean allparts = true;
			for(int i = 0; i < types.length; i++) {
				Part part = requestPart(types[i]);
				if(part == null) {
					returnParts();
					allparts = false;
					break;
				}
				sort(part);
				storage[i] = part;
			}
			if(allparts)
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
