package telran.tests;

import java.util.Comparator;

public class ComparatorOdd implements Comparator<Object> {

	@Override
	public int compare(Object o1, Object o2) {
        int num1 = (int) o1;
        int num2 = (int) o2;
        int res = 0;
        if (num1 > num2) {
        	res = 1;
        }
        
        if (num1 < num2) {
        	res = -1;
        }
		return res;
	}

}
