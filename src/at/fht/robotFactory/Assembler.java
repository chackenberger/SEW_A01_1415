package at.fht.robotFactory;

import java.io.IOException;
import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import at.hackenberger.lib.Watchable;

/**
 * 
 * Runnable which assembles Threadees
 * @author Fock Hagen
 * @version 1.0
 */
public class Assembler extends Employee implements Runnable, Watchable {
	
	private Part[] storage;
	
	private Logger logger;
	private boolean shutdown = false;
	
	public Assembler() {
		logger = LogManager.getLogger(this.getClass().getName() + "(" + this.getID()  + ")");
		this.storage = new Part[PartType.values().length + 2]; //+2 because eye and arm are needed twice
		
	}
	
	/**
	 * Requests a Part from the Storageguy
	 * @param type the PartType of the requested Part
	 * @return the requested Part
	 */
	public Part requestPart(PartType type) {
		logger.info("request " + type + " from storage");
		return Factory.getStorage().getPart(type);
	}
	
	/**
	 * returns all Parts which are currently in the storage of this Assembler to the Storageguy
	 * @throws IOException when the Storageguy fails to save the parts
	 */
	public void returnParts() throws IOException{
		logger.warn("could not get all parts for a Threadee return current parts to storage");
		Storageguy sg = Factory.getStorage();
		for (int i = 0; i < storage.length; i++){
			sg.storePart(storage[i]);
			storage[i] = null;
		}
	}

	/**
	 * Sorts the numbers array of a port as a symbol of assembling
	 * @param part the which should be assembled
	 */
	public void sort(Part part) {
		logger.info("sorting Part " + part.getPartType());
		int[] sortHelp;
		sortHelp = part.getNumbers();
		Arrays.sort(sortHelp);
		part.setNumbers(sortHelp);
	}
	
	/**
	 * Give the assembled Threadee to the Storageguy for saving
	 * @param parts the parts of which the Threadee consits
	 * @throws IOException when the Storageguy fails to save the parts
	 */
	public void robotArchive(Part[] parts) throws IOException{
		logger.info("send new Threadee to storage");
		Factory.getStorage().storeThreadee(this.getID(), parts);
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Runnable#run()
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
					while(!shutdown) {
						try {
							returnParts();
							break;
						}catch (IOException ex) {
							logger.error("Could not save parts back to storage! Trying again...");
						}
					}
					allparts = false;
					break;
				}
				sort(part);
				storage[i] = part;
			}
			if(allparts) {
				while(!shutdown) {
					try {
						robotArchive(storage);
						break;
					}catch (IOException ex) {
						logger.error("Could not save robot in storage! Trying again...");
					}
				}
			}
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see at.hackenberger.lib.Watchable#shutdown()
	 */
	@Override
	public boolean shutdown() {
		return (this.shutdown = true);
	}
}
