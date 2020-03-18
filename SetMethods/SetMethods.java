package util;

public class SetMethods {
    /**
     * Assumption: no repeated numbers in each array, but
     * numbers in first array may be repeated in the second
     *
     * @param ar1 - first array
     * @param ar2 - second array
     * @return array containing numbers of first and second arrays
     * with no repetitions
     */
    public static int[] union(int ar1[], int ar2[]) {
        //T O D O
        HashSet<Integer> setInt = new HashSet<>();
        for (int i = 0; i < ar1.length; i++) {
            setInt.add(ar1[i]);
        }
        for (int j = 0; j < ar2.length; j++) {
            setInt.add(ar2[j]);
        }
        int[] res = new int[setInt.size()];
        int i = 0;
        for (int num : setInt) {
            res[i++] = num;
        }
        return res;
    }

    /**
     * Assumption: no repeated numbers in each array, but
     * numbers in first array may be repeated in the second
     *
     * @param ar1 - first array
     * @param ar2 - second array
     * @return array containing common numbers between first and second arrays
     * with no repetitions
     */
    public static int[] intersection(int ar1[], int ar2[]) {
        HashSet<Integer> setFirst = new HashSet<>();
        for (int i = 0; i < ar1.length; i++) {
            setFirst.add(ar1[i]);
        }

        HashSet<Integer> setRes = new HashSet<>();
        for (int j = 0; j < ar2.length; j++) {
            int num = ar2[j];
            if (setFirst.contains(num)) setRes.add(num);
        }

        int[] res = new int[setRes.size()];
        int i = 0;
        for (int num : setRes) {
            res[i++] = num;
        }

        return res;
    }

    /**
     * Assumption: no repeated numbers in each array, but
     * numbers in first array may be repeated in the second
     *
     * @param ar1 - first array
     * @param ar2 - second array
     * @return array containing numbers of first array that are not repeated
     * in the second
     */
    public static int[] difference(int ar1[], int ar2[]) {
        //T O D O;
        HashSet<Integer> setFirst = new HashSet<>();
        for (int i = 0; i < ar2.length; i++) {
            setFirst.add(ar2[i]);
        }

        HashSet<Integer> setRes = new HashSet<>();
        for (int i = 0; i < ar1.length; i++) {
            int num = ar1[i];
            if (!setFirst.contains(num)) setRes.add(num);
        }

        int[] res = new int[setRes.size()];
        int i = 0;
        for (int num : setRes) {
            res[i++] = num;
        }

        return res;
    }
}
