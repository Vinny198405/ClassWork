package telran.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;
import java.util.function.Predicate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import telran.util.HashSet;
import telran.util.Set;
import telran.util.SortedSet;
import telran.util.TreeSet;

class SetTests {
	Integer numbers[] = { 10, 20, 11, -8, 7, 13, 9, 100, 2, 70, 15, 21, 121, 500 };
	Set<Integer> set;

	@BeforeEach
	void setUp() {
		set = new TreeSet<Integer>();
		for (Integer num : numbers) {
			set.add(num);
		}
	}

	@Test
	void testAddContains() {
		for (Integer num : numbers) {
			assertTrue(set.contains(num));
		}
		assertFalse(set.contains(8));
		assertFalse(set.add(-8));
		assertTrue(set.add(8));
	}

	void setAdd(Integer[] numbers) {
		set = new HashSet<Integer>();
		for (Integer num : numbers) {
			set.add(num);
		}
	}

	@Test
	void testFilter() {
		Set<Integer> evenSet = set.filter(new EvenNumbersPredicate());
		Integer[] evenNumbers = { 10, 20, -8, 100, 2, 70, 500 };
		testSetArray(evenSet, evenNumbers);
	}

	@Test
	void testRemoveIf() {
		Predicate<Integer> predicateEven = new EvenNumbersPredicate();
		Integer[] oddNumbers = { 11, 7, 13, 9, 15, 21, 121 };
		assertTrue(set.removeIf(predicateEven));
		testSetArray(set, oddNumbers);
		for (int i = 0; i < 10000; i++) {
			set.add(getRandomNumber(0, Integer.MAX_VALUE));
		}
		set.removeIf(predicateEven);
		for (int num : set) {
			assertTrue(num % 2 == 1);
		}
	}

	private Integer getRandomNumber(int min, int max) {
		return (int) (min + Math.random() * (max - min));
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
	void testRemoveObj() {
		Integer[] numbersNo100 = { 10, 20, 11, 7, 13, 9, 2, 70, 15, 21, 121 };
		assertEquals(100, set.remove(100));
		assertNull(set.remove(100));
		assertEquals(500, set.remove(500));
		assertEquals(-8, set.remove(-8));
		testSetArray(set, numbersNo100);
	}

	@Test
	void testRemoveAll() {
		set.removeIf(new TruePredicate());
		assertEquals(0, set.size());
	}

	@Test
	void testIterator() {
		testSetArray(set, numbers);
	}

	private void testSetArray(Set<Integer> setTest, Integer[] numbersExpected) {
		Integer[] numbersActual = new Integer[setTest.size()];
		int ind = 0;
		for (Integer num : setTest) {
			numbersActual[ind++] = num;

		}
		Arrays.sort(numbersExpected);
		Arrays.sort(numbersActual);
		assertArrayEquals(numbersExpected, numbersActual);

	}

	@Test
	void iterator() {
		int expectedNumbers[] = RandomArray(0, 10000, 1000);
		set = new HashSet<Integer>();
		for (Integer num : expectedNumbers) {
			set.add(num);
		}
		int[] actual = new int[set.size()];
		int j = 0;
		for (Iterator<Integer> itr = set.iterator(); itr.hasNext();) {
			actual[j++] = itr.next();
		}
		Arrays.sort(actual);
		assertArrayEquals(actual, expectedNumbers);
	}

	/**
	 * function to create a randomized sorted array without repeating numbers
	 * 
	 * @param start - minimal number
	 * @param end   - maximum number
	 * @param count - numbers in the array
	 * @return the function return sorted random array
	 */
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

	// test for SortedTest
	@Test
	void testMinElement() {
		if (set instanceof SortedSet) {
			SortedSet<Integer> sortedSet = (SortedSet<Integer>) set;
			assertEquals(-8, sortedSet.getMin());
		}
	}

	@Test
	void testMaxElement() {
		if (set instanceof SortedSet) {
			SortedSet<Integer> sortedSet = (SortedSet<Integer>) set;
			assertEquals(500, sortedSet.getMax());
		}
	}

	@Test
	void testHead() {
		if (set instanceof SortedSet) {
			SortedSet<Integer> sortedSet = (SortedSet<Integer>) set;
			Integer[] lessThan10 = { 2, -8, 7, 9 };
			Integer[] lessThanEqual10 = { 2, -8, 7, 9, 10 };
			testSetArray(sortedSet.head(10, false), lessThan10);
			testSetArray(sortedSet.head(10, true), lessThanEqual10);
		}
	}

	@Test
	void testTail() {
		if (set instanceof SortedSet) {
			SortedSet<Integer> sortedSet = (SortedSet<Integer>) set;
			Integer[] greater100 = { 121, 500 };
			Integer[] greaterEqual100 = { 100, 121, 500 };
			testSetArray(sortedSet.tail(100, false), greater100);
			testSetArray(sortedSet.tail(100, true), greaterEqual100);	
			testSetArray(sortedSet.tail(90, true), greaterEqual100);
		}
	}
    @Test
    void testSubset() {
        if (set instanceof SortedSet) {
            SortedSet<Integer> sortedSet = (SortedSet<Integer>) set;
            Integer[] openRange15_100 = {20, 70, 21};
            Integer[] closeRange15_100 = {20, 70, 21, 15, 100};
            testSetArray(sortedSet.subset(15, false, 100, false), openRange15_100);
            testSetArray(sortedSet.subset(15, true, 100, true), closeRange15_100);
            testSetArray(sortedSet.subset(14, true, 110, true), closeRange15_100);
        }
    }
}
