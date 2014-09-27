package at.fht.robotFactory;

/**
 * 
 * @author Christoph Hackenberger
 * Represents a Part of a Threadee
 */
public class Part {

	private PartType type;
	private int[] numbers;
	
	/**
	 * Creates a new Part object
	 * @param type of the Part
	 * @param numbers the numbers which must be sorted to assemble the part
	 */
	public Part(PartType type, int[] numbers) {
		this.type=type;
		this.numbers = numbers;
	}
	
	/**
	 * Returns the PartType of the Part
	 * @return the PartType
	 */
	public PartType getPartType() {
		return this.type;
	}
	
	/**
	 * Returns the numbers of the Part
	 * @return the numbers
	 */
	public int[] getNumbers() {
		return this.numbers;
	}
	
	/**
	 * Set the numbers of the Part
	 * @param numbers the new numbers for this part
	 */
	public void setNumbers(int[] numbers) {
		this.numbers = numbers;
	}
}
