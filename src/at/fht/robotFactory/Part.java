package at.fht.robotFactory;

public class Part {

	private PartType type;
	private int[] numbers;
	
	public Part(PartType type, int[] numbers) {
		this.type=type;
		this.numbers = numbers;
	}
	
	public PartType getPartType() {
		return this.type;
	}
	
	public int[] getNumbers() {
		return this.numbers;
	}
	
	public void setNumbers(int[] numbers) {
		this.numbers = numbers;
	}
}
