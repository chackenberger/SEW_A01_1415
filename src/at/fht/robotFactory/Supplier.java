package at.fht.robotFactory;

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
	private Part[] p;
	private boolean shutdown = false;

	public Supplier() {
		super();
		logger = LogManager.getLogger(this.getClass().getName() + "(" + this.getID() + ")");
	}
	
	/**
	 * Adds all the Parts for one Threadee to an Array for Parts
	 * stores all Parts via Storageguy
	 */
	@Override
	public void run() {
		while (!shutdown) {
			this.p[0] = new Part(PartType.ARM, genNumbers());
			this.p[1] = new Part(PartType.ARM, genNumbers());
			this.p[2] = new Part(PartType.EYE, genNumbers());
			this.p[3] = new Part(PartType.EYE, genNumbers());
			this.p[4] = new Part(PartType.GEAR, genNumbers());
			this.p[5] = new Part(PartType.BODY, genNumbers());
			for (int i = 0; i < p.length; i++) {
				Factory.getStorage().storePart(p[i]);
				p[i] = null;
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