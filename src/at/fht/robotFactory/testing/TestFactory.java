package at.fht.robotFactory.testing;

import static org.junit.Assert.*;

import org.junit.Test;

import at.fht.robotFactory.Factory;

public class TestFactory {

	@Test
	public void testIfWatchdogShutsdownAllThreads() throws InterruptedException{
		Factory.testMainWithThreads("-a 10 -l lager -o logs/log --lieferanten 5 -t 3000");
		new Thread(new Factory()).start();
		Thread.sleep(4000);
		
		for(Thread t : Factory.getThreads())
			assertEquals(false, t.isAlive());
	}

}
