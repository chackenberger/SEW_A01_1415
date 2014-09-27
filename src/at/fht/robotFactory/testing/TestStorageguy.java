package at.fht.robotFactory.testing;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import at.fht.robotFactory.Part;
import at.fht.robotFactory.PartType;
import at.fht.robotFactory.Storageguy;

public class TestStorageguy {

	private Storageguy g;
	
	@Before
	public void setup() {
		try {
			File dir = new File("/Users/Christoph/Desktop/stguy");
			for (File f : dir.listFiles())
				f.delete();
			dir.delete();
		}catch (Exception ex){}
		g = new Storageguy("/Users/Christoph/Desktop/stguy");
	}
	
	@Test
	public void testCreateDirs() {
		new Storageguy("/Users/Christoph/Desktop/stguy");
	}
	
	@Test
	public void testWritePartInEveryFile() {
		for(PartType type : PartType.values())
			g.storePart(new Part(type, new int[]{0,1,2,3,4}));
	}
	
	@Test
	public void testReadPartFromEveryFile() {
		//setup
		Part[] ps = new Part[PartType.values().length];
		int i = 0;
		for(PartType type : PartType.values()) {
			Part p = new Part(type, new int[]{0,1,2,3,4});
			ps[i] = p;
			i++;
			g.storePart(p);
		}
		
		assertEquals(ps[0], g.getPart(ps[0].getPartType()));
		assertEquals(ps[1], g.getPart(ps[1].getPartType()));
		assertEquals(ps[2], g.getPart(ps[2].getPartType()));
		assertEquals(ps[3], g.getPart(ps[3].getPartType()));
	}
	
	@Test
	public void testWriteThreadee() {
		
		Part[] ps = new Part[PartType.values().length];
		int i = 0;
		for(PartType type : PartType.values()) {
			Part p = new Part(type, new int[]{0,1,2,3,4});
			ps[i] = p;
			i++;
		}
		
		g.storeThreadee(1, ps);
	}
}
