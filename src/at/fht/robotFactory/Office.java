package at.fht.robotFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 * The central point where all employees can request IDs and also IDs for Threadees
 * @author Seyyid Tiryaki
 * @version 1.0
 */

public class Office
{
	private Logger logger;
	
	private long employeeID;
	private long threadeeID;
	
	/**
	 * Creates a new Office object
	 */
	public Office()
	{
		logger = LogManager.getLogger(this.getClass().getName());
		employeeID = 0;
		threadeeID = 0;
	}
	
	/**
	 * Request an ID for an employee
	 * @return ID for employee
	 */
	public synchronized long requestID()
	{	
		employeeID++;
		logger.info("send ID for employee: " +  employeeID);
		return employeeID;
		
	}
	
	/**
	 * Request an ID for a Threadee
	 * @return ID for Threadee
	 */
	public synchronized long requestRobotID()
	{
		threadeeID++;
		logger.info("send ID for robot: " +  threadeeID);
		return threadeeID;
	}
}
