package xl.learn;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuelin on 5/10/17.
 */
public class ReversePair {
    public static int countInStripe(int[] ints, int low, int high) {
        return -1;
    }

    public static int count(int[] ints) {
        //ascending strips s 0-k
        //for strip si, l to h, looking for reverse pairs starts in this stripe,
        //so look for substripes which lies between l/2 to h/2

        List<Integer> boundary = new ArrayList<>();
        for (int ii = 1; ii < ints.length; ii++) {
            if (ints[ii - 1] > ints[ii]) {
                boundary.add(ii);
            }
        }
        boundary.add(ints.length);

        int total = 0;

        int low = 0;
        for (int ii = 0; ii < boundary.size(); ii++) {
            int bb = boundary.get(ii);
            int high = bb - 1;
            total += countInStripe(ints, low, high);

            for (int jj = 0; jj < ii; jj++) {

            }

        }

        return -1;

    }
}
