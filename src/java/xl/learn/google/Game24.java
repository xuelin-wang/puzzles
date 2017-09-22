package xl.learn.google;

import com.google.common.base.Joiner;
import com.google.common.primitives.Doubles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by xuelin on 9/21/17.
 *
 * Naive:
 * Permutation(4,2) * 4
 * then P(3,2) * 4
 * then P(2,2) * 4
 * if see 24, true. otherwise false.
 *
 *
 */
public class Game24 {
    private static final int[] initial = new int[]{0, 1, 0};

    /**'
     * int[] has three elements,
     * int[0]: first operand index
     * int[1]: second operand iondex
     * int[2]: operator. 0: +, 1: -, 2: *, 3: /.
     * @param curr
     * @param n
     * @return
     */
    private static int[] moveNext(int[] curr, int n) {
        if (curr == null)
            return new int[]{0, 1, 0};

        int a = curr[0];
        int b = curr[1];
        int op = curr[2];
        if (op < 3)
            return new int[]{a, b, op+1};

        int newOp = 0;
        int newB = a == b + 1 ? b + 2 : b + 1;
        if (newB < n)
            return new int[]{a, newB, newOp};

        int newA = a + 1;
        if (newA >= n)
            return null;

        newB = 0;
        return new int[]{newA, newB, newOp};
    }

    private static double calc(int[] calc, double[] nums) {
        double op1 = nums[calc[0]];
        double op2 = nums[calc[1]];
        double res;
        switch (calc[2]) {
            case 0:
                res = op1 + op2;
                break;
            case 1:
                res = op1 - op2;
                break;
            case 2:
                res = op1 * op2;
                break;
            case 3:
                res = op1 / op2;
                break;
            default:
                throw new IllegalArgumentException("op must be between 0 and 3");
        }
        return res;
    }

    private static double[] getNums(double[] origNums, int[] calc, double res) {
        double[] newNums = new double[origNums.length - 1];
        for (int i = 0, j = 0; j < newNums.length - 1; j++, i++) {
            while (i == calc[0] || i == calc[1])
                i++;
            newNums[j] = origNums[i];
        }
        newNums[newNums.length - 1] = res;
        return newNums;
    }
    private static int[][] moveNext(int[][] calcs) {
        int[] calc2 = moveNext(calcs[2], 2);
        if (calc2 != null)
            return new int[][]{calcs[0], calcs[1], calc2};

        calc2 = initial;
        int[] calc1 = moveNext(calcs[1], 3);
        if (calc1 != null)
            return new int[][]{calcs[0], calc1, calc2};
        calc1 = initial;

        int[] calc0 = moveNext(calcs[0], 4);
        if (calc0 == null)
            return null;
        return new int[][]{calc0, calc1, calc2};
    }

    public static boolean solve(double[] nums) {
        int[][] calcs = new int[][]{initial, initial, initial};

        while (true) {
            double res0 = calc(calcs[0], nums);
            double[] nums1 = getNums(nums, calcs[0], res0);
            double res1 = calc(calcs[1], nums1);
            double[] nums2 = getNums(nums1, calcs[1], res1);
            double res2 = calc(calcs[2], nums2);
            if (Math.abs(res2 - 24) < 0.000001) {
                return true;
            }
            calcs = moveNext(calcs);
            if (calcs == null)
                break;
        }
        return false;
    }

    public static boolean solve2(List<Double> ds) {
        if (ds.size() == 0)
            return false;
        if (ds.size() == 1) {
            return Math.abs(ds.get(0).doubleValue() - 24) < 0.000001;
        }
        for (int i = 0; i < ds.size(); i++) {
            for (int j = 0; j < ds.size(); j++) {
                if (i == j)
                    continue;

                double a = ds.get(i);
                double b = ds.get(j);
                for (int op = 0; op < 4; op++) {
                    double res;
                    switch (op) {
                        case 0:
                            res = a + b;
                            break;
                        case 1:
                            res = a - b;
                            break;
                        case 2:
                            res = a * b;
                            break;
                        default:
                            res = a / b;
                    }
                    ArrayList<Double> ds2 = new ArrayList<>();
                    for (int k = 0; k < ds.size(); k++) {
                        if (k == i || k == j)
                            continue;
                        ds2.add(ds.get(k));
                    }
                    ds2.add(res);
                    if (solve2(ds2))
                        return true;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        for (double[] nums: new double[][]{
                new double[]{4, 1, 8, 7},
                new double[]{1, 2, 1, 2}
        }) {
            System.out.println(Doubles.asList(nums).stream().map(x -> String.valueOf(x)).collect(Collectors.joining(", ")));
            System.out.println("Can get 24: " + solve2(Doubles.asList(nums)));
        }
    }
}
