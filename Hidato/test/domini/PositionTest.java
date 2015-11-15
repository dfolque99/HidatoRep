package domini;

import org.junit.*;
import static org.junit.Assert.*;

/**
 * The class <code>PositionTest</code> contains tests for the class <code>{@link Position}</code>.
 *
 * @generatedBy CodePro at 15/11/15 12:37
 * @author keur
 * @version $Revision: 1.0 $
 */
public class PositionTest {
	/**
	 * Run the Position(Integer,Integer) constructor test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 15/11/15 12:37
	 */
	@Test
	public void testPosition_1()
		throws Exception {
		Integer x = new Integer(1);
		Integer y = new Integer(1);

		Position result = new Position(x, y);

		// add additional test code here
		assertNotNull(result);
		assertEquals(new Integer(1), result.getX());
		assertEquals(new Integer(1), result.getY());
	}

	/**
	 * Run the Position add(Position,Position) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 15/11/15 12:37
	 */
	@Test
	public void testAdd_1()
		throws Exception {
		Position p1 = new Position(new Integer(1), new Integer(1));
		Position p2 = new Position(new Integer(1), new Integer(1));

		Position result = Position.add(p1, p2);

		// add additional test code here
		assertNotNull(result);
		assertEquals(new Integer(2), result.getX());
		assertEquals(new Integer(2), result.getY());
	}

	/**
	 * Run the Integer distance(Position,Position) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 15/11/15 12:37
	 */
	@Test
	public void testDistance_1()
		throws Exception {
		Position p1 = new Position(new Integer(1), new Integer(1));
		Position p2 = new Position(new Integer(1), new Integer(1));

		Integer result = Position.distance(p1, p2);

		// add additional test code here
		assertNotNull(result);
		assertEquals("0", result.toString());
		assertEquals((byte) 0, result.byteValue());
		assertEquals((short) 0, result.shortValue());
		assertEquals(0, result.intValue());
		assertEquals(0L, result.longValue());
		assertEquals(0.0f, result.floatValue(), 1.0f);
		assertEquals(0.0, result.doubleValue(), 1.0);
	}

	/**
	 * Run the boolean equals(Object) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 15/11/15 12:37
	 */
	@Test
	public void testEquals_1()
		throws Exception {
		Position fixture = new Position(new Integer(1), new Integer(1));
		Object obj = null;

		boolean result = fixture.equals(obj);

		// add additional test code here
		assertEquals(false, result);
	}

	/**
	 * Run the boolean equals(Object) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 15/11/15 12:37
	 */
	@Test
	public void testEquals_2()
		throws Exception {
		Position fixture = new Position(new Integer(1), new Integer(1));
		Object obj = new Object();

		boolean result = fixture.equals(obj);

		// add additional test code here
		assertEquals(false, result);
	}

	/**
	 * Run the boolean equals(Object) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 15/11/15 12:37
	 */
	@Test
	public void testEquals_3()
		throws Exception {
		Position fixture = new Position(new Integer(1), new Integer(1));
		Object obj = new Position(new Integer(1), new Integer(1));

		boolean result = fixture.equals(obj);

		// add additional test code here
		assertEquals(true, result);
	}

	/**
	 * Run the boolean equals(Object) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 15/11/15 12:37
	 */
	@Test
	public void testEquals_4()
		throws Exception {
		Position fixture = new Position(new Integer(1), new Integer(1));
		Object obj = new Position(new Integer(1), new Integer(1));

		boolean result = fixture.equals(obj);

		// add additional test code here
		assertEquals(true, result);
	}

	/**
	 * Run the boolean equals(Object) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 15/11/15 12:37
	 */
	@Test
	public void testEquals_5()
		throws Exception {
		Position fixture = new Position(new Integer(1), new Integer(1));
		Object obj = new Position(new Integer(1), new Integer(1));

		boolean result = fixture.equals(obj);

		// add additional test code here
		assertEquals(true, result);
	}

	/**
	 * Run the Integer getX() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 15/11/15 12:37
	 */
	@Test
	public void testGetX_1()
		throws Exception {
		Position fixture = new Position(new Integer(1), new Integer(1));

		Integer result = fixture.getX();

		// add additional test code here
		assertNotNull(result);
		assertEquals("1", result.toString());
		assertEquals((byte) 1, result.byteValue());
		assertEquals((short) 1, result.shortValue());
		assertEquals(1, result.intValue());
		assertEquals(1L, result.longValue());
		assertEquals(1.0f, result.floatValue(), 1.0f);
		assertEquals(1.0, result.doubleValue(), 1.0);
	}

	/**
	 * Run the Integer getY() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 15/11/15 12:37
	 */
	@Test
	public void testGetY_1()
		throws Exception {
		Position fixture = new Position(new Integer(1), new Integer(1));

		Integer result = fixture.getY();

		// add additional test code here
		assertNotNull(result);
		assertEquals("1", result.toString());
		assertEquals((byte) 1, result.byteValue());
		assertEquals((short) 1, result.shortValue());
		assertEquals(1, result.intValue());
		assertEquals(1L, result.longValue());
		assertEquals(1.0f, result.floatValue(), 1.0f);
		assertEquals(1.0, result.doubleValue(), 1.0);
	}

	/**
	 * Run the int hashCode() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 15/11/15 12:37
	 */
	@Test
	public void testHashCode_1()
		throws Exception {
		Position fixture = new Position(new Integer(1), new Integer(1));

		int result = fixture.hashCode();

		// add additional test code here
		assertEquals(22513, result);
	}

	/**
	 * Run the Integer norm(Position) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 15/11/15 12:37
	 */
	@Test
	public void testNorm_1()
		throws Exception {
		Position p = new Position(new Integer(1), new Integer(1));

		Integer result = Position.norm(p);

		// add additional test code here
		assertNotNull(result);
		assertEquals("1", result.toString());
		assertEquals((byte) 1, result.byteValue());
		assertEquals((short) 1, result.shortValue());
		assertEquals(1, result.intValue());
		assertEquals(1L, result.longValue());
		assertEquals(1.0f, result.floatValue(), 1.0f);
		assertEquals(1.0, result.doubleValue(), 1.0);
	}

	/**
	 * Run the boolean notEnoughDistance(int,Position,int,Position) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 15/11/15 12:37
	 */
	@Test
	public void testNotEnoughDistance_1()
		throws Exception {
		int n = 1;
		Position p1 = new Position(new Integer(1), new Integer(1));
		int m = 1;
		Position p2 = new Position(new Integer(1), new Integer(1));

		boolean result = Position.notEnoughDistance(n, p1, m, p2);

		// add additional test code here
		assertEquals(false, result);
	}

	/**
	 * Run the boolean notEnoughDistance(int,Position,int,Position) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 15/11/15 12:37
	 */
	@Test
	public void testNotEnoughDistance_2()
		throws Exception {
		int n = 1;
		Position p1 = new Position(new Integer(1), new Integer(1));
		int m = 1;
		Position p2 = new Position(new Integer(1), new Integer(1));

		boolean result = Position.notEnoughDistance(n, p1, m, p2);

		// add additional test code here
		assertEquals(false, result);
	}

	/**
	 * Run the Position symmetric(Position) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 15/11/15 12:37
	 */
	@Test
	public void testSymmetric_1()
		throws Exception {
		Position p1 = new Position(new Integer(1), new Integer(1));

		Position result = Position.symmetric(p1);

		// add additional test code here
		assertNotNull(result);
		assertEquals(new Integer(-1), result.getX());
		assertEquals(new Integer(-1), result.getY());
	}

	/**
	 * Perform pre-test initialization.
	 *
	 * @throws Exception
	 *         if the initialization fails for some reason
	 *
	 * @generatedBy CodePro at 15/11/15 12:37
	 */
	@Before
	public void setUp()
		throws Exception {
		// add additional set up code here
	}

	/**
	 * Perform post-test clean-up.
	 *
	 * @throws Exception
	 *         if the clean-up fails for some reason
	 *
	 * @generatedBy CodePro at 15/11/15 12:37
	 */
	@After
	public void tearDown()
		throws Exception {
		// Add additional tear down code here
	}

	/**
	 * Launch the test.
	 *
	 * @param args the command line arguments
	 *
	 * @generatedBy CodePro at 15/11/15 12:37
	 */
	public static void main(String[] args) {
		new org.junit.runner.JUnitCore().run(PositionTest.class);
	}
}