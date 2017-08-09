package xl.learn;

public class TwoKeysKeyboard {
    /**
     *
     * Initially on a notepad only one character 'A' is present.
     * You can perform two operations on this notepad for each step:
     * Copy All: You can copy all the characters present on the notepad
     * (partial copy is not allowed).
     * Paste: You can paste the characters which are copied last time.
     * Given a number n. You have to get exactly n 'A' on the notepad
     * by performing the minimum number of steps permitted.
     * Output the minimum number of steps to get n 'A'.
     *
     * last step must be paste
     * Assume the number of chars in last paste is k,
     * then copyAll happens when there are k As on notepad.
     * there are (n - k) / k pastes.
     * f(n) = min f(k) + (n-k)/k | k > 0 and k < n
     * f(1) = 0
     * f(2) = 1
     * f(3) = 3
     *
     * @param n
     * @return
     */
    public static int minSteps(int n)
    {
        int[] cache = new int[n];
        cache[0] = 0;
        cache[1] = 2;
        for (int i = 2; i < n; i++) {
            int curr = Integer.MAX_VALUE;
            for (int j = 0; j < i && (i -j) % (j + 1) == 0; j++) {
                int tmp = cache[j] + 1 + (i - j) / (j + 1);
                if (curr > tmp)
                    curr = tmp;
            }
            cache[i] = curr;
        }
        return cache[n - 1];
     }

     public static void main(String[] args) {
        for (int n: new int[]{3, 4, 5, 6, 7, 8}) {
            System.out.println("min steps for " + n + " is " + minSteps(n));
        }
     }
}
