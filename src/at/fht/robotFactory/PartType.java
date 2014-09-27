package at.fht.robotFactory;

/**
 * 
 * @author Hackenberger Christoph
 * Saves the diffrent PartTypes and also their fileName in the storage directory
 */
public enum PartType {
	
	ARM("arm.csv"),
	EYE("eye.csv"),
	GEAR("gear.csv"),
	BODY("body.csv");
	
	private final String fileName;
	
	private PartType(String fileName) {
		this.fileName = fileName;
	}
	
	/**
	 * Returns the file name for this PartType
	 * @return the file name for this PartType
	 */
	public String getFileName() {
		return this.fileName;
	}
}
