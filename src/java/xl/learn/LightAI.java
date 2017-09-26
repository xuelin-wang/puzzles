package xl.learn;

/**
 * Created by xuelin on 9/25/17.
 */
public class LightAI {

    private int distance(int i, int j) {
        if (i + j == 7)
            return 2;
        else
            return 1;
    }

    public int solution(int[] A) {
        int[] moves = new int[6];
        for (int i = 1; i < 7; i++) {
            int sum = 0;
            for (int k = 0; k < A.length; k++){
                sum += distance(A[k], i);
            }
            moves[i-1] = sum;
        }

        int min = Integer.MAX_VALUE;
        for (int i = 0; i < 6; i++) {
            if (moves[i] < min)
                min = moves[i];
        }
        return min;
    }
}
