package at.fht.robotFactory;

import org.apache.logging.log4j.Logger;

import com.sun.swing.internal.plaf.synth.resources.synth;

public class Office
{
	private Logger logger;
	
	private long employeeID;
	private long threadeeID;
	
	public Office()
	{
		
	}
	
	public synchronized long requestID()
	{
		return 0;
	}
	
	public synchronized long requestRobotID()
	{
		return 0;
	}
}