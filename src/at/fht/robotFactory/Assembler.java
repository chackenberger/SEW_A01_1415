package at.fht.robotFactory;

import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import at.hackenberger.lib.Watchable;

public class Assembler extends Employee implements Runnable, Watchable {
	
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
		// STORGAEDIR?????
		Storageguy sg = new Storageguy(null);
		return sg.getPart(type);
	}
	/**
	 * returns Parts back to the SstorageGuy if the 
	 * Assembler didnt got all requierd Parts
	 */
	private void returnParts(){
		Storageguy sg = new Storageguy(null);
		for (int i = 0; i < storage.length; i++){
			sg.storePart(storage[i]);
		}
	}
	/**
	 * sorts numbers as a symbol of assembling
	 * @param type
	 */
	private void sort(Part part) {
		logger = LogManager.getLogger(this.getClass().getName());
		int[] sortHelp;
		sortHelp = part.getNumbers();
		Arrays.sort(sortHelp);
		part.setNumbers(sortHelp);
	}

	private void robotArchive(Part[] parts){
		
	}

	@Override
	public void run() {
//  Storage[] mit all den Koerperteilen fuer den Threadee auff�llen
//	storage[i].requestPart(ARM,AUGE...)	 
	}

	@Override
	public boolean shutdown() {
		return false;
	}
}
