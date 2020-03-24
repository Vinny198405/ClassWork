package telran.util;

public interface SortedSet<T> extends Set<T> {
    T getMin(); // minimal element

    T getMax(); // maximal element

    SortedSet<T> subset(T from, boolean isIncludedFrom, T to, boolean isIncludedTo);//range either open or close range

    default SortedSet<T> head(T key, boolean isInclude) {//all elements that either strong less or less/equal than key
        return this.subset(getMin(),true,key,isInclude);
    }
    default SortedSet<T> tail(T key, boolean isInclude){//all elements that either strong greater or greater/equal than key
        return this.subset(key,isInclude,getMax(),true);
    }
}
