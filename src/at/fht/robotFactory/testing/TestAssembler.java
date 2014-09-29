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
	 * have to put the modifier to public otherwise it wont work
	 */
	@Test
	public void testSort1() {
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
	/**
	 * have to put the modifier to public otherwise it wont work
	 */
	@Test
	public void testSort2() {
		int[] i = {1,3,5,3,1};
		Part p = new Part(PartType.EYE , i);
		a.sort(p);
		int[] test1;
		test1 = p.getNumbers();
		for (int j = 0; j<test1.length; j++) {
			System.out.println(test1[j]);
		}
		int[] test2 = {1,1,3,3,5}; 
		assertArrayEquals(test1, test2);
	}
}
