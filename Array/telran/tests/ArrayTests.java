package telran.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Comparator;

import org.junit.jupiter.api.Test;

import telran.util.Array;

class ArrayTests {
	int numbers[] = {10, -8, 70, 75, 30};
	int numbers1[] = {10, 15, 50, 55, 25, 20, 40, 45, 35, 30};
	@Test
	void testAddGetSize() {
		Array array = getArray(numbers);
		for (int i = 0; i < array.size(); i++) {
			assertEquals(numbers[i], array.get(i));
		}
		assertNull(array.get(array.size()));
		
	}
	private Array getArray(int ar[]) {
		Array array = new Array(ar.length);
		
		for (int i = 0; i < ar.length; i++) {
			array.add(ar[i]);
		}
		return array;
	}
	
	/*private Array getArray() {
		Array array = new Array(4);
		
		for (int i = 0; i < numbers.length; i++) {
			array.add(numbers[i]);
		}
		return array;
	}*/
	@Test
	void testAddAtIndex() {
		int expectedNumbers[] = {-10, 10, -8, 70, -70, 75, 30, -30};
		Array array = getArray(numbers);
		assertTrue(array.add(0, -10));
		assertTrue(array.add(4, -70));
		assertTrue(array.add(7, -30));
		int actualNumbers[] = getActualNumbers(array);
		assertArrayEquals(expectedNumbers, actualNumbers );
		assertFalse(array.add(-1, 100));
		assertFalse(array.add(100, 100));
		
	}
	private int[] getActualNumbers(Array array) {
		int res[] = new int[array.size()];
		for (int i = 0; i < res.length; i++) {
			res[i] = (int) array.get(i);
		}
		return res;
	}
	@Test
	void testRemoveAtIndex() {
		int expectedNumbers[] = { -8, 75};
		Array array = getArray(numbers);
		assertEquals(10, array.remove(0));
		assertEquals(70, array.remove(1));
		assertEquals(30, array.remove(2));
		assertArrayEquals(expectedNumbers, getActualNumbers(array));
		assertNull(array.remove(2));
		assertNull(array.remove(-1));
	}
	@Test
	void testSetAtIndex() {
		int expectedNumbers[] = {-10, -8, -70, 75, -30};
		Array array = getArray(numbers);
		assertEquals(10, array.set(0, -10));
		assertEquals(70, array.set(2, -70));
		assertEquals(30, array.set(4, -30));
		assertArrayEquals(expectedNumbers, getActualNumbers(array));
		assertNull(array.set(-1, 100));
		assertNull(array.set(100, 100));
	}
	@Test
	void testSorting() {
		
		Person personMoshe = new Person(123, "Moshe", 1980);
		Person personVova = new Person(100, "Vova", 1970);
		Array array = new Array();
		array.add(personMoshe);
		array.add(personVova);
		array.sort();
		assertEquals(personVova, array.get(0));
		assertEquals(personMoshe, array.get(1));
		array.sort(new PersonAgeComparator());
		assertEquals(personVova, array.get(1));
		assertEquals(personMoshe, array.get(0));
		
	}
	@Test
	void testBinarySearch() {
		String stringsNaturalOrder[]=
			{"abcd","lm", "lmnopr","x","y","z"};
		String stringsLengthOrder[]=
			{"x","y","z","lm","abcd", "lmnopr"};
		Comparator<Object> compLength = new StringLengthComparator();
		Array stringsNatural = getArrayStrings(stringsNaturalOrder);
		Array stringsLength = getArrayStrings(stringsLengthOrder);
		assertEquals(-3, stringsNatural.binarySearch("lmn"));
		assertEquals(1, stringsNatural.binarySearch("lm"));
		assertEquals(-5, stringsLength.binarySearch("lmn", compLength));
		assertEquals(3, stringsLength.binarySearch("lm", compLength ));
	}
	private Array getArrayStrings(String[] strings) {
		Array array = new Array(strings.length);
		for (int i = 0; i < strings.length; i++) {
			array.add(strings[i]);
		}
		return array;
	}
	@Test
	void testFilter() {
		Array array = getArray(numbers);
		int expected[] = {10, -8, 70, 30};
		Array arrayNoEven =
				array.filter(new EvenNumbersPredicate());
		int actualNumbers[] = getActualNumbers(arrayNoEven);
		assertArrayEquals(expected, actualNumbers);
	}
	@Test
	void testRemoveIf() {
		Array array = getArray(numbers);
		int expected[] = {75};
		assertTrue(array.removeIf(new EvenNumbersPredicate()));	
		int actualNumbers[] = getActualNumbers(array);
		assertArrayEquals(expected, actualNumbers);
	}
	@Test
	/**
	 * additional test for sorting array numbers according to the following
	 * all odd numbers should go before the even ones
	 * odd numbers should be sorted in the ascending order
	 * even numbers should be sorted in the descending order
	 */
	void testSortingEvenOdd() {
		Array array = getArray(numbers1);
		int expectedEven[] = {50,40,30,20,10};
		int expectedOdd[] = {15,25,35,45,55};
		int expectedEvenOdd[] = {15,55,25,45,35,10,50,20,40,30};
		int expectedEvenOddSort[] = {15,25,35,45,55,50,40,30,20,10};
		
				
		Array arrayeven = array.filter(new EvenNumbersPredicate()); //choose even numbers
		Array arrayodd = array.filter(new OddNumbersPredicate()); //choose odd numbers	

		arrayeven.sort(new ComparatorEven());  // sorted even numbers
		int actualNumbersEven[] = getActualNumbers(arrayeven);
		assertArrayEquals(expectedEven, actualNumbersEven);
		
		arrayodd.sort(new ComparatorOdd()); // sorted odd numbers
		int actualNumbersOdd[] = getActualNumbers(arrayodd);
		assertArrayEquals(expectedOdd, actualNumbersOdd);
		
		array.sort(new OddEvenComparator()); // all odd numbers should go before the even ones
		int actualNumbersEvenOdd[] = getActualNumbers(array);
		assertArrayEquals(actualNumbersEvenOdd, expectedEvenOdd);
		
		// all odd numbers go before the even ones 
		// and odd numbers be sorted in the ascending order
		// and even numbers be sorted in the descending order
		array.sort(new OddEvenComparatorSort()); 
		int actualNumbersEvenOddSort[] = getActualNumbers(array);
		assertArrayEquals(actualNumbersEvenOddSort, expectedEvenOddSort);		
	
	}
	@Test
	void testSortingEvenOddd() {
		Array array = getArray(numbers1);
		int expectedEvenOddSort[] = {15,25,35,45,55,50,40,30,20,10};
		
		array.sortingEvenOdd(new EvenNumbersPredicate());
		int actualNumbersEvenOdd[] = getActualNumbers(array);
		assertArrayEquals(actualNumbersEvenOdd, expectedEvenOddSort);
	}
		
}
