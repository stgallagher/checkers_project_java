package checkers;

import static org.junit.Assert.*;
import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

public class CheckerTest extends TestCase{
	
	private Checker checker;

	@Before
	protected void setUp(){
		checker = new Checker(2, 3, "Red");
	}

	@Test
	public void testChecker() {
		assertEquals(2, checker.x_pos);
		assertEquals(3, checker.y_pos);
		assertEquals("Red", checker.color);
	}

	@Test
	public void testIsKing() {
		assertEquals(false, checker.isKing());
	}

	@Test
	public void testMakeKing() {
		assertEquals(false, checker.isKing());
		checker.makeKing();
		assertEquals(true, checker.isKing());
	}
}
