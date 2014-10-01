package at.fht.robotFactory;

import java.util.Arrays;

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
		return this.numbers.clone();
	}
	
	/**
	 * Set the numbers of the Part
	 * @param numbers the new numbers for this part
	 */
	public void setNumbers(int[] numbers) {
		this.numbers = numbers;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(numbers);
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Part))
			return false;
		Part other = (Part) obj;
		if (!Arrays.equals(numbers, other.numbers))
			return false;
		if (type != other.type)
			return false;
		return true;
	}
}
