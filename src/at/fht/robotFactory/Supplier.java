package at.fht.robotFactory;

import org.apache.logging.log4j.Logger;

import at.hackenberger.lib.Watchable;
/**
 * 
 * @author FOCK
 *
 */
public class Supplier implements Runnable, Watchable {
	
	private Logger logger;
	
	public Supplier() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void run() {
		genNumbers();
		
	}
	/**
	 * generates random numbers for the Parts
	 * @return int[]
	 */
	public int[] genNumbers() {
		int[] storage = new int[20];
		for (int i = 0; i <20; i++) {
			storage[i] = (int)((Math.random()*100)+1);
		}
		return storage;
	}

	@Override
	public boolean shutdown() {
		// TODO Auto-generated method stub
		return false;
	}
}