package at.fht.robotFactory;

import java.io.IOException;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import at.hackenberger.lib.Watchable;

/**
 * 
 * @author FOCK
 *
 */
public class Supplier extends Employee implements Runnable, Watchable {

	private Logger logger;
	private boolean shutdown = false;

	public Supplier() {
		logger = LogManager.getLogger(this.getClass().getName() + "(" + this.getID() + ")");
	}
	
	/**
	 * creates an Array filled with PartTypes
	 * then one Type is selected with a random Number
	 * Afterwards a loop stores 10 selected PartTypes via Storageguy
	 */
	@Override
	public void run() {
		while (!shutdown) {
			System.out.println("test");
			PartType pt[] = PartType.values();
			Random r = new Random();
			int zz = r.nextInt(pt.length);
			for (int i = 0; i < 10; i++){
				Part p = new Part(pt[zz], genNumbers());
				logger.info("Generated new " + pt[zz]);
				try {
					logger.info("Saving " + pt[zz] + " to storage");
					Factory.getStorage().storePart(p);
				} catch (IOException e) {
					logger.error("Could not save part to storage!");
				}
			}
		}
	}

	/**
	 * generates random numbers for the Parts
	 * 
	 * @return int[]
	 */
	public int[] genNumbers() {
		logger.info("generates numbers for a part");
		int[] storage = new int[20];
		for (int i = 0; i < 20; i++) {
			storage[i] = (int) ((Math.random() * 100) + 1);
		}
		return storage;
	}

	@Override
	public boolean shutdown() {
		return (this.shutdown = true);
	}
}