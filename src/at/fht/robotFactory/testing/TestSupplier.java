package at.fht.robotFactory.testing;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import at.fht.robotFactory.Factory;
import at.fht.robotFactory.Part;
import at.fht.robotFactory.PartType;
import at.fht.robotFactory.Supplier;

public class TestSupplier {
	
	private Supplier s;
	
	@Before
	public void setup(){
		Factory.testMain("-a 10 -l lager -o logs/log --lieferanten 5 -t 20000");
		this.s = new Supplier();
	}
	
	
	@Test
	public void testGenNumbers(){
		int[] test = this.s.genNumbers();
		assertEquals(test.length, 20);
	}
	@Test
	public void testSupplier(){
		Supplier sp = new Supplier();
	}
	
	@Test
	public void tryIfThreadsShutsdownIn1Second() throws InterruptedException{
		Thread t = new Thread(s);
		t.start();
		Thread.sleep(100);
		s.shutdown();
		Thread.sleep(1000);
		assertEquals(false, t.isAlive());
	}
}
