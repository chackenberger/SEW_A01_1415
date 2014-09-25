package tgm.sew.hit.roboterfabrik;

public class Supplier implements Runnable {
	public int[] storage;

	@Override
	public void run() {
		genNumbers();
		
	}
	/**
	 * generates random numbers for the Parts
	 * @return
	 */
	public int[] genNumbers() {
		this.storage = new int[20];
		for (int i = 0; i <20; i++) {
			storage[i] = (int)((Math.random()*100)+1);
		}
		/* fürs testen
		for (int j = 0; j<storage.length; j++) {
			System.out.print(storage[j]+", ");
		}
		System.out.println();
		*/
		return null;
	}
}