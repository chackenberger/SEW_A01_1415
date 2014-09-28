package at.fht.robotFactory;

public class Factory implements Runnable {

	public static Factory instance;
	
	private static Supplier[] suppliers;
	private static Assembler[] assemblers;
	
	private static Office office = new Office();
	private static Storageguy storage;
	
	private static Logger logger;
	
	public static void main(String[] args) {
		
	}
	
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
