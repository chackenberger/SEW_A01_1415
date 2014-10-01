package at.fht.robotFactory;

import org.apache.logging.log4j.LogManager;
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
		logger = LogManager.getLogger(this.getClass().getName());
		employeeID = 0;
		threadeeID = 0;
	}
	/*
	 * @return ID for employee
	 */
	public synchronized long requestID()
	{	
		employeeID++;
		logger.info("send ID for employee: " +  employeeID);
		return employeeID;
		
	}
	
	/*
	 * @return ID for Robot
	 */
	public synchronized long requestRobotID()
	{
		threadeeID++;
		logger.info("send ID for robot: " +  threadeeID);
		return threadeeID;
	}
}
