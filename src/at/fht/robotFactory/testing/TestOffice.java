package at.fht.robotFactory.testing;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import at.fht.robotFactory.Part;
import at.fht.robotFactory.PartType;
import at.fht.robotFactory.Assembler;
import at.fht.robotFactory.Office;

public class TestOffice {

	private Office c;
	private Assembler as;
	
	@Before
	public void setup()
	{
		c = new Office();
		as = new Assembler();
	}
	
	@Test
	public void testRequestID() {
		as.setID(c.requestID());
	}

}
