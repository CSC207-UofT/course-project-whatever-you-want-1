package constants;

import graph.Vertex;
import graph.VertexArray;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Iterator;

public class Algorithm {
    public static <T> int recBinaryInsertIndex(List<T> lst, T toInsert, Comparator<T> comparator, int b, int e) {
        if (b == e) {
            if (comparator.compare(toInsert, lst.get(b)) < 0) {
                return b;
            } else {
                return e + 1;
            }
        } else {
            int m = (b + e) / 2;
            if (comparator.compare(toInsert, lst.get(m)) < 0) {
                return recBinaryInsertIndex(lst, toInsert, comparator, b, m);
            } else {
                return recBinaryInsertIndex(lst, toInsert, comparator, m + 1, e);
            }
        }
    }

    public static <T> int pairwiseCompare(Iterator<T> itr1, Iterator<T> itr2, Comparator<T> comparator) {
        while (itr1.hasNext() && itr2.hasNext()) {
            T next1 = itr1.next();
            T next2 = itr2.next();
            if (comparator.compare(next1, next2) != 0) {
                return comparator.compare(next1, next2);
            }
        }

        if (!(itr1.hasNext() || itr2.hasNext())) {
            return 0;
        } else if (itr2.hasNext()) {
            return -1;
        } else {
            return 1;
        }
    }

    public static <T> void mergeSort(List<T> unsorted, int b, int e, Comparator<T> comparator) {
        if (b == e) {
            return;
        }
        int m = (b + e) / 2;
        mergeSort(unsorted, b, m, comparator);
        mergeSort(unsorted, m + 1, e, comparator);
        merge(unsorted, b, m, e, comparator);
    }

    // Citation: the merge method was paraphrased form
    // https://www.withexample.com/merge-sort-using-arraylist-java-example/
    private static <T> void merge(List<T> unsorted, int b, int m, int e, Comparator<T> comparator) {
        List<T> merged = new ArrayList<>();
        int l = b;
        int r = m + 1;
        while(l <= m && r <= e) {
            if (comparator.compare(unsorted.get(l), unsorted.get(r)) <= 0) {
                merged.add(unsorted.get(l));
                l ++;
            } else {
                merged.add(unsorted.get(r));
                r ++;
            }
        }
        while (l <= m) {
            merged.add(unsorted.get(l));
            l ++;
        }
        while (r <= e) {
            merged.add(unsorted.get(r));
            r ++;
        }
        int i = 0;
        int j = b;
        while (i < merged.size()) {
            unsorted.set(j, merged.get(i));
            i ++;
            j ++;
        }
    }

}
