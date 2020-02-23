package telran.tests;

import java.util.Comparator;

public class PersonAgeComparator implements Comparator<Object>{

	@Override
	public int compare(Object o1, Object o2) {
		return ((Person)o1).getBirthYear() >= ((Person)o2).getBirthYear() ? -1 : 1;
	}

}
