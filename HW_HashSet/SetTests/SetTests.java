package telran.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import telran.util.HashSet;
import telran.util.Set;

class SetTests {
	Integer numbers[] = {10, 20, 11, -8, 7, 13};
Set<Integer> set;
@BeforeEach
void setUp() {
	set = new HashSet<Integer>();
	for (Integer num: numbers) {
		set.add(num);
	}
}
	@Test
	void testAddContains() {
		for (Integer num: numbers) {
			assertTrue(set.contains(num));
		}
		assertFalse(set.contains(8));
		assertFalse(set.add(-8));
		assertTrue(set.add(8));
	}
	@Test
    void removeIf() {    
		// {11, 7, 13};
        EvenNumbersPredicate predicateEven = new EvenNumbersPredicate();
        assertTrue(set.removeIf(predicateEven));
        assertFalse(set.removeIf(predicateEven));
        assertEquals(3, set.size());
        assertTrue(set.contains(11));
        assertTrue(set.contains(7));
        assertTrue(set.contains(13));
        assertFalse(set.contains(-8));
    }
	@Test
	void filter() {
	        //{10, 20, -8};
	        EvenNumbersPredicate predicateEven = new EvenNumbersPredicate();
	        HashSet<Integer> res = (HashSet<Integer>) set.filter(predicateEven);
	        assertEquals(3, res.size());
	        assertTrue(res.contains(10));
	        assertTrue(res.contains(20));
	        assertTrue(res.contains(-8));
	        assertFalse(res.contains(11));
	        for (Integer value : res) {
				assertTrue(set.contains(value));
			}
    }
	@Test
    void remove() {
        assertTrue(set.add(8));
        assertTrue(set.contains(8));
        assertEquals(8, set.remove(8));
        assertFalse(set.contains(8));
        assertEquals(null, set.remove(-50));
    }
	
	@Test
    void iterator() {
		int expectedNumbers[] = RandomArray(0, 10000, 50);
		set = new HashSet<Integer>();
		for (Integer num: expectedNumbers) {
			set.add(num);
		}
		int[] actual = new int[set.size()];
        int j = 0;
        for (Iterator<Integer> itr = set.iterator(); itr.hasNext();) {
            actual[j++] = itr.next();
        }
        Arrays.sort(actual);
        Arrays.sort(expectedNumbers);
        assertArrayEquals(actual, expectedNumbers);
	}
	
	public static int[] RandomArray(int start, int end, int count) {
	    Random rng = new Random();

	    int[] result = new int[count];
	    int cur = 0;
	    int remaining = end - start;
	    for (int i = start; i < end && count > 0; i++) {
	        double probability = rng.nextDouble();
	        if (probability < ((double) count) / (double) remaining) {
	            count--;
	            result[cur++] = i;
	        }
	        remaining--;
	    }
	    return result;
	}
	
	
}
