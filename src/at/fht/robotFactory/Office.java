package at.fht.robotFactory;

import org.apache.logging.log4j.Logger;
/*
 * @author Seyyid Tiryaki
 * @description The Office awards IDs for the employees and robots
 */

public class Office
{
	private Logger logger;
	
	private long employeeID;
	private long threadeeID;
	
	public Office()
	{
		employeeID = 0;
		threadeeID = 0;
	}
	
	public synchronized long requestID()
	{	
		return employeeID++;
	}
	
	public synchronized long requestRobotID()
	{
		return threadeeID++;
	}
}