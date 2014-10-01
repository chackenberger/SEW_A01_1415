package at.fht.robotFactory.testing;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import at.fht.robotFactory.Factory;
import at.fht.robotFactory.Part;
import at.fht.robotFactory.PartType;
import at.fht.robotFactory.Storageguy;

public class TestStorageguy {

	private Storageguy g;
	
	@Before
	public void setup() {
		Factory.testMain("-a 10 -l lager -o logs/log --lieferanten 5 -t 20000");
		try {
			File dir = new File("/Users/Christoph/Desktop/stguy");
			for (File f : dir.listFiles())
				f.delete();
			dir.delete();
		}catch (Exception ex){}
		g = Factory.getStorage();
	}
	
	@Test
	public void testCreateDirs() throws IOException {
		new Storageguy("testlager");
		//Lager wieder löschen
		try{
			File dir = new File("lager");
			for (File f : dir.listFiles())
				f.delete();
			dir.delete();
		}catch (Exception ex){}
	}
	
	@Test
	public void testWritePartInEveryFile() throws IOException {
		for(PartType type : PartType.values())
			g.storePart(new Part(type, new int[]{0,1,2,3,4}));
	}
	
	@Test
	public void testReadPartFromEveryFile() throws IOException {
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
	public void testWriteThreadee() throws IOException {
		
		Part[] ps = new Part[PartType.values().length];
		int i = 0;
		for(PartType type : PartType.values()) {
			Part p = new Part(type, new int[]{0,1,2,3,4});
			ps[i] = p;
			i++;
		}
		
		g.storeThreadee(1, ps);
	}
	
	@Test(expected=IOException.class)
	public void testInvalidStorageDir() throws IOException {
		new Storageguy("//test");
	}
	
	@Test
	public void testTryReadingPartButDeleteFiles() throws IOException {
		//setup
		Part[] ps = new Part[PartType.values().length];
		int i = 0;
		for(PartType type : PartType.values()) {
			Part p = new Part(type, new int[]{0,1,2,3,4});
			ps[i] = p;
			i++;
			g.storePart(p);
		}
		
		//now delete Files
		try {
			File dir = new File("lager");
			for (File f : dir.listFiles())
				f.delete();
			dir.delete();
		}catch (Exception ex){}
		
		assertEquals(null, g.getPart(ps[0].getPartType()));
		assertEquals(null, g.getPart(ps[1].getPartType()));
		assertEquals(null, g.getPart(ps[2].getPartType()));
		assertEquals(null, g.getPart(ps[3].getPartType()));
	}
	
	@Test(expected=IOException.class)
	public void testTryStoringPartsButDeleteFiles() throws IOException {
		//setup
				Part[] ps = new Part[PartType.values().length];
				int i = 0;
				for(PartType type : PartType.values()) {
					Part p = new Part(type, new int[]{0,1,2,3,4});
					ps[i] = p;
					i++;
					g.storePart(p);
				}
				
				//now delete Files
				try {
					File dir = new File("lager");
					for (File f : dir.listFiles())
						f.delete();
					dir.delete();
				}catch (Exception ex){}
				
				g.storePart(ps[0]);
	}
	
	@Test(expected=IOException.class)
	public void testTryStoringThreadeeButDeleteFiles() throws IOException {
		//setup
		Part[] ps = new Part[PartType.values().length];
		int i = 0;
		for(PartType type : PartType.values()) {
			Part p = new Part(type, new int[]{0,1,2,3,4});
			ps[i] = p;
			i++;
			g.storePart(p);
		}
		
		//now delete Files
		try {
			File dir = new File("lager");
			for (File f : dir.listFiles())
				f.delete();
			dir.delete();
		}catch (Exception ex){}
		
		g.storeThreadee(0, ps);
	}
	
	@Test
	public void testReadingAnInvalidLine() throws IOException {
		String t = "\"arm\",\"a\"";
		File dir = new File("lager/arm.csv");
		FileOutputStream out = new FileOutputStream(dir, false);
		out.write(t.getBytes());
		out.flush();
		out.close();
		
		Part p = g.getPart(PartType.ARM);
		
		assertEquals(null, p);
	}
	
}
