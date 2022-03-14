/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package euromillions;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import sets.SetOfNaturals;

/**
 * @author ico0
 */
public class DipTest {

    private Dip instance;
    public DipTest() {
    }

    @BeforeEach
    public void setUp() {
        instance = new Dip(new int[]{10, 20, 30, 40, 50}, new int[]{1, 2});
    }

    @AfterEach
    public void tearDown() {
        instance = null;
    }

    @Test
    public void testConstructorFromBadArrays() {

        SetOfNaturals numbers = instance.getNumbersColl();
        SetOfNaturals stars = instance.getStarsColl();

        assertEquals(5, numbers.size());
        assertEquals(2, stars.size());

        for (Integer star: stars)
            assertFalse(star < 0 || star > 12);
        for (Integer number: numbers)
            assertFalse(number < 0 || number > 50);
    }

    @Test
    public void testFormat() {
        String result = instance.format();
        assertEquals("N[ 10 20 30 40 50] S[  1  2]", result, "format as string: formatted string not as expected. ");
    }

}
