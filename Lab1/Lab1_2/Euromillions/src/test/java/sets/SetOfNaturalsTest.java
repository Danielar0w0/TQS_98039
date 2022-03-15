/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sets;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

/**
 * @author ico0
 */
public class SetOfNaturalsTest {
    private SetOfNaturals setA;
    private SetOfNaturals setB;
    private SetOfNaturals setC;
    private SetOfNaturals setD;

    @BeforeEach
    public void setUp() {
        setA = new SetOfNaturals();
        setB = SetOfNaturals.fromArray(new int[]{10, 20, 30, 40, 50, 60});

        setC = new SetOfNaturals();
        for (int i = 5; i < 50; i++) {
            setC.add(i * 10);
        }
        setD = SetOfNaturals.fromArray(new int[]{30, 40, 50, 60, 10, 20});
    }

    @AfterEach
    public void tearDown() {
        setA = setB = setC = setD = null;
    }

    @Test
    public void testAddElement() {

        setA.add(99);
        assertTrue(setA.contains(99), "add: added element not found in set.");
        assertEquals(1, setA.size());

        setB.add(11);
        assertTrue(setB.contains(11), "add: added element not found in set.");
        assertEquals(7, setB.size(), "add: elements count not as expected.");
    }

    @Test
    public void testAddBadArray() {
        int[] elems = new int[]{10, 20, -30};

        // must fail with exception
        assertThrows(IllegalArgumentException.class, () -> setA.add(elems));
    }

    @Test
    public void testFromArray() {
        int[] elems = new int[]{10, 20, 30, 40, 50, 60};
        for (int e: elems)
            setA.add(e);

        assertEquals(setB, setA);
    }

    @Test
    public void testSize() {

        int[] elems = new int[]{10, 20, 30};
        setA = SetOfNaturals.fromArray(elems);

        assertEquals(elems.length, setA.size());
    }

    @Test
    public void testIntersects() {

        int[] elems = new int[]{10, 2};
        setA = SetOfNaturals.fromArray(elems);

        assertTrue(setA.intersects(setB));
    }

    @Test
    public void testContains() {
        setA.add(5);
        assertTrue(setA.contains(5));
    }

    @Test
    public void testIterator() {

        int[] elems = new int[]{10, 20, 30};
        setA = SetOfNaturals.fromArray(elems);

        Iterator<Integer> it = setA.iterator();

        int i = 0; int el, el2;

        while (it.hasNext()) {

            el = it.next();
            el2 = -1;

            if (i < elems.length)
                el2 = elems[i];

            assertEquals(el2, el);
            i += 1;
        }
    }

    @Test
    public void TestHashCode() {

        ArrayList<Integer> array = new ArrayList<>(Arrays.asList(10, 20, 30, 40, 50, 60));
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(array);

        int result = setB.hashCode();
        assertEquals(hash, result);
    }

    @Test
    public void testIntersectForNoIntersection() {
        assertFalse(setA.intersects(setB), "no intersection but was reported as existing");

    }


}
