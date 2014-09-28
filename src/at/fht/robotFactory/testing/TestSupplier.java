package at.fht.robotFactory.testing;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import at.fht.robotFactory.Part;
import at.fht.robotFactory.PartType;
import at.fht.robotFactory.Supplier;

public class TestSupplier {
	
	private Supplier s;
	
	@Before
	public void setup(){
		this.s = new Supplier();
	}
	
	
	@Test
	public void testGenNumbers(){
		int[] test = this.s.genNumbers();
		assertEquals(test.length, 20);
	}
}
