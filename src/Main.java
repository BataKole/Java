import hashtable.FenwickTreeRangeUpdatePointQuery;

import java.util.Iterator;

class Main {


    public static void main(String[] args) {

        // The values array must be one based
        long[] values = {0,+1,-2,+3,-4,+5,-6};
//               ^ first element does not get used

        FenwickTreeRangeUpdatePointQuery ft = new FenwickTreeRangeUpdatePointQuery(values);

        ft.updateRange(1, 4, 10); // Add +10 to interval [1, 4] in O(log(n))
        ft.get(1); // 11
        ft.get(4); // 6
        ft.get(5); // 5

        ft.updateRange(3, 6, -20); // Add -20 to interval [1, 4] in O(log(n))
        ft.get(3); // -7
        ft.get(4); // -14
        ft.get(5); // -15

    }

}