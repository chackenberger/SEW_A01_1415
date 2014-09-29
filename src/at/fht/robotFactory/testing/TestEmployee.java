package at.fht.robotFactory.testing;

import static org.junit.Assert.*;

import org.junit.Test;
import at.fht.robotFactory.*;
import org.junit.Before;

public class TestEmployee {

	Employee e;
	
	@Before
	public void setup() {
		e = new Employee();
	}

	@Test
	public void testID() {
		e.setID(1);
		e.getID();
	}
}
