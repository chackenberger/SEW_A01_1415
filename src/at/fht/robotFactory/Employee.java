package at.fht.robotFactory;
/**
 * 
 * Represents an general employee of the robotfactory 
 * @author Seyyid Tiryaki
 * @version 1.0
 */
public class Employee
{
	private long iD;

	/**
	 * Creates a new Employee object
	 */
	public Employee()
	{
		this.setID(Factory.getOffice().requestID());
	}
	
	/**
	 * Retruns the ID of the Employee
	 * @return the ID of the Employee
	 */
	public long getID() 
	{
		return iD;
	}
	
	/**
	 * Sets the ID of the Employee to the specified value
	 * @param id the ID of the Employee
	 */
	public void setID(long id) 
	{
		this.iD = id;
	}
	
}
