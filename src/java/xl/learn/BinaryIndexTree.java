package xl.learn;

import com.google.common.base.Joiner;
import com.google.common.primitives.Ints;

/**
 * Created by xuelin on 7/23/17.
 * tree[idx] = sum from (idx - 2^r + 1) to idx, idx starting from 1, r being position of last 1 bit in idx
 */
public class BinaryIndexTree {

    private static int getParent(int i) {
        return i + (i & -i);
    }
    private static int removeLast1(int i) {
        return i - (i & -i);
    }

    private static void update(int[] tree, int idx, int delta) {
        int i = idx;
        while (true) {
            tree[i] += delta;
            i = getParent(i + 1);
            if (i >= tree.length + 1)
                break;
            i--;
        }
    }

    private static int getSum(int to, int[] tree)
    {
        if (to < 0)
            return 0;
        int sum = 0;
        int i = to;
        while (true) {
            sum += tree[i];
            i = removeLast1(i + 1);
            if (i == 0)
                break;
            i--;
        }
        return sum;
    }

    public static int[] buildTree(int[] nums) {
        int[] tree = new int[nums.length];
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            int from = removeLast1(i + 1);
            sum += nums[i];
            tree[i] = sum - getSum(from - 1, tree);
        }
        return tree;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{1,	0,	2,	1,	1,	3,	0,	4,	2,	5,	2,	2,	3,	1,	0,	2};
        int[] tree = buildTree(nums);
        System.out.println(Joiner.on(", ").join(Ints.asList(tree)));

        update(tree, 8, 2);
        System.out.println(Joiner.on(", ").join(Ints.asList(tree)));
    }
}
