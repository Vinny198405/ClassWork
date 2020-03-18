package tests;

import org.junit.jupiter.api.Test;
import util.SetMethods;

import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;

public class SetMethodsTests {

    @Test
    void testUnion() {
        int ar1[] = {10, 30, -8, 20};
        int ar2[] = {0, -3, 7, 11};
        int ar3[] = {0, -8, 20, 10};
        int exp1[] = {10, 30, -8, 20, 0, -3, 7, 11};
        int exp2[] = {10, 30, -8, 20, 0};
        Arrays.sort(exp1);
        Arrays.sort(exp2);
        int res1[] = SetMethods.union(ar1, ar2);
        Arrays.sort(res1);
        assertArrayEquals(exp1, res1);
        int res2[] = SetMethods.union(ar1, ar3);
        Arrays.sort(res2);
        assertArrayEquals(exp2, res2);
    }

    @Test
    void testIntersection() {
        int ar1[] = {10, 30, -8, 20};
        int ar2[] = {0, -3, 7, 11};
        int ar3[] = {0, -8, 20, 10};
        int exp1[] = {};
        int exp2[] = {10, -8, 20};
        assertArrayEquals(exp1, SetMethods.intersection(ar1, ar2));
        int res1[] = SetMethods.intersection(ar1, ar3);
        Arrays.sort(exp2);
        Arrays.sort(res1);
        assertArrayEquals(exp2, res1);
    }

    @Test
    void testDifference() {
        int ar1[] = {10, 30, -8, 20};
        int ar2[] = {0, -3, 7, 11};
        int ar3[] = {0, -8, 20, 10};
        int exp1[] = ar1;
        int exp2[] = {30};
        Arrays.sort(exp1);
        int res1[] = SetMethods.difference(ar1, ar2);
        Arrays.sort(res1);
        assertArrayEquals(exp1, res1);
        assertArrayEquals(exp2, SetMethods.difference(ar1, ar3));
    }
}
