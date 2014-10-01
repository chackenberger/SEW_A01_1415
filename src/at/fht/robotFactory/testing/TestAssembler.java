package at.fht.robotFactory.testing;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import at.fht.robotFactory.Factory;
import at.fht.robotFactory.Part;
import at.fht.robotFactory.PartType;
import at.fht.robotFactory.Assembler;

public class TestAssembler {
	private Assembler a;
	
	@Before
	public void setup() {
		Factory.testMain("-a 10 -l lager -o logs/log --lieferanten 5 -t 20000");
		a = new Assembler();
	}
	
	@Test
	public void testSort() {
		int[] t = new int[]{5,7,2,8,9}; 
		Part p = new Part(PartType.BODY, t);
		a.sort(p);
		
		assertArrayEquals(new int[]{2,5,7,8,9}, p.getNumbers());
	}
	
	@Test
	public void testRequestPart() throws IOException{
		for(PartType type : PartType.values())
			Factory.getStorage().storePart(new Part(type, new int[]{1,2,3}));
		
		Part arm = a.requestPart(PartType.ARM);
		Part eye = a.requestPart(PartType.EYE);
		Part body = a.requestPart(PartType.BODY);
		Part gear = a.requestPart(PartType.GEAR);
		
		assertEquals(PartType.ARM, arm.getPartType());
		assertEquals(PartType.EYE, eye.getPartType());
		assertEquals(PartType.BODY, body.getPartType());
		assertEquals(PartType.GEAR, gear.getPartType());
		a.returnParts();
	}

	/**
	 * have to put the modifier to public otherwise it wont work
	 */
	@Test
	public void testRobotArchiveNoErrors() throws IOException {
		Part[] parts = new Part[]{a.requestPart(PartType.ARM), a.requestPart(PartType.EYE)};
		a.robotArchive(parts);
	}
	
	@Test
	public void tryIfThreadsShutsdownIn1Second() throws InterruptedException{
		Thread t = new Thread(a);
		t.start();
		Thread.sleep(100);
		a.shutdown();
		Thread.sleep(1000);
		assertEquals(false, t.isAlive());
	}
}
