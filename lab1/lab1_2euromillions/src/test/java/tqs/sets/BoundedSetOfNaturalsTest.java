/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tqs.sets;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;

import org.junit.jupiter.api.*;

/**
 * @author ico0
 */
class BoundedSetOfNaturalsTest {
    private BoundedSetOfNaturals setA;
    private BoundedSetOfNaturals setB;
    private BoundedSetOfNaturals setC;
    private BoundedSetOfNaturals setD;


    @BeforeEach
    public void setUp() {
        setA = new BoundedSetOfNaturals(1);
        setB = BoundedSetOfNaturals.fromArray(new int[]{10, 20, 30, 40, 50, 60});
        setC = BoundedSetOfNaturals.fromArray(new int[]{50, 60});
        setD = new BoundedSetOfNaturals(3);
    }

    @AfterEach
    public void tearDown() {
        setA = setB = setC = null;
    }

    @Test
    public void testAddElement() {

        setA.add(99);
        assertTrue(setA.contains(99), "add: added element not found in set.");
        assertEquals(1, setA.size());

        // sets created from arrays don't accept new elements, fot their maximum size is given by the array length
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> setB.add(11));
        Assertions.assertEquals("bounded set is full. no more elements allowed.", ex.getMessage());
        assertEquals(6, setB.size(), "add: elements count not as expected.");
    }

    @Test
    public void testAddFromNegArray() {
        int[] negElems = new int[]{10, -20, -30};

        // must fail with exception
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> setD.add(negElems));
        Assertions.assertEquals("Illegal argument: not a natural number", ex.getMessage());

    }

    @Test
    public void testAddFromDupArray() {
        int[] dupElems = new int[]{10, 10, 20};

        // must fail with exception
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> setD.add(dupElems));
        Assertions.assertEquals("duplicate value: 10", ex.getMessage());
        
    }

    @Test
    public void testAddFromGoodArray() {
        int[] elems = new int[]{10, 70, 30};
        setD.add(elems);
        assertTrue(setD.contains(70), "add: added element not found in set.");
        assertEquals(3, setD.size());
    }


    @Test
    public void testIntersect() {
        assertTrue(setB.intersects(setC));
        assertTrue(setC.intersects(setB));
        assertFalse(setA.intersects(setC));
    }

    @Test
    public void testHashCodeAndEquals() {
        setD.add( new int[]{50, 60});
        assertTrue(setD.equals(setC) && setC.equals(setD));
        assertEquals(setD.hashCode(), setC.hashCode());
        assertTrue(setD.equals(setD));
        assertFalse(setD.equals(null));
        assertFalse(setD.equals(setA));
        assertFalse(setD.equals(new HashSet<>()));
    }
}
