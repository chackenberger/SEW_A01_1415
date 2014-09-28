package at.fht.robotFactory.testing;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import at.fht.robotFactory.Part;
import at.fht.robotFactory.PartType;
import at.fht.robotFactory.Assembler;

public class TestAssembler {
	private Assembler a;
	
	@Before
	public void setup() {
		a = new Assembler();
	}
	
	
	/**
	 * had to put the modifier to public... forgot how to test private methodes
	 */
	@Test
	public void testSort() {
		int[] i = {1,6,3,4,2};
		Part p = new Part(PartType.EYE , i);
		a.sort(p);
		int[] test1;
		test1 = p.getNumbers();
		for (int j = 0; j<test1.length; j++) {
			System.out.println(test1[j]);
		}
		int[] test2 = {1,2,3,4,6}; 
		assertArrayEquals(test1, test2);
	}
}
