package telran.tests;

import java.util.function.Predicate;

public class EvenNumbersPredicate implements Predicate<Integer> {

	@Override
	public boolean test(Integer num) {
		
		return num % 2 == 0;
	}

}
