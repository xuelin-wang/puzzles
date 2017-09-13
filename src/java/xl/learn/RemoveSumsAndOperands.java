package xl.learn;

import com.google.common.base.Joiner;
import com.google.common.primitives.Ints;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by xuelin on 9/11/17.
 * Given num list ns,
 * if a group of nums form ns, where all but one add up to the other one in the group, can remove.
 * return rest of ns where no group removal possible.
 */
public class RemoveSumsAndOperands {
    public static List<Integer> removeSums(List<Integer> ns) {
        Collections.sort(ns); //sort speed up checking sum, it must be after last addend
        int n = ns.size();
        // check sum of 2, 3, up to n-1, if their sum is in rest of ns, remove the group.
        for (int k = 2; k < n - 1; k++) {
            int[] group = new int[k];
            for (int i = 0; i < k; i++)
                group[i] = i;

            boolean done = false;

            while (!done) {
                int sum = 0;
                for (int m: group) {
                    sum += ns.get(m);
                }
                for (int s = group[k-1] + 1; s < n; s++) {
                    if (sum == ns.get(s).intValue()) {
                        //remove group and s
                        ns.remove(s);
                        for (int r = k - 1; r >= 0; r--) {
                            ns.remove(group[r]);
                        }
                        return removeSums(ns);
                    }
                }

                for (int i = k - 1; i >= 0; i--) {
                    if (group[i] < n - 2 - (k - 1 - i)) {
                        group[i] ++;
                        for (int j = i+1; j<k; j++) {
                            group[j] = group[j-1] + 1;
                        }
                        break;
                    }
                    if (i == 0) {
                        done = true;
                    }
                }
            }
        }

        return ns;
    }

    public static void main(String[] args) {
        for (int[] nums: new int[][]{
                {1, 3, 5, 6},
                {48, 20, 1, 3, 4, 6, 9, 24},
        }) {
            System.out.println("Remove from group: " + Joiner.on(", ").join(Ints.asList(nums)));
            List<Integer> results = removeSums(new ArrayList<>(Ints.asList(nums)));
            System.out.println(Joiner.on(", ").join(results));
        }
    }
}
