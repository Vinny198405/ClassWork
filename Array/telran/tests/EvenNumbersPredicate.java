package telran.tests;

import java.util.function.Predicate;

public class EvenNumbersPredicate implements Predicate<Object> {

	@Override
	public boolean test(Object arg0) {
		int num = (int)arg0;
		return num % 2 == 0;
	}

}
