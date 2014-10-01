package at.fht.robotFactory;
/*
 * @author Seyyid Tiryaki
 * @description All Workers extend from the Employee 
 */
public class Employee
{
	private long iD;

	public Employee()
	{
		this.setID(Factory.getOffice().requestID());
	}
	
	/*
	 * @return get ID from the Employee
	 */
	public long getID() 
	{
		return iD;
	}
	
	/*
	 * @return set a new ID for the Employee
	 */
	public void setID(long l) 
	{
		this.iD = l;
	}
	
}
