package at.fht.robotFactory;

public class Factory implements Runnable {

	public static Factory instance;
	
	private Supplier[] suppliers;
	private Assembler[] assemblers;
	
	private static Office office = new Office();
	private static Storageguy storage;
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
	public static Office getOffice() {
		return office;
	}
	
	public static Storageguy getStorage() {
		return storage;
	}
}
