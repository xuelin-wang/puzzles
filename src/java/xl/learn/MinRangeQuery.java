package xl.learn;

/**
 * Created by xuelin on 7/23/17.
 * Array A[0..n-1], query min elements in i..j
 * Algorithms:
 * 1. naive,
 * Precompute all queries, {O(n^2), O(1)}
 *     Precompute: q[i,i] = A[i].
 *     q[i, j] = A[j]|A[j] < q[i, j-1], q[i, j-1] | otherwise
 * 2. Aquare root sections: {O(n, O(n^1/2)}
 *     Precompute min in each of n^1/2 sections, each has n^1/2 elements: O(n)
 *     query:
 * 3. Spart table algorithm
 * Precompute all ranges with length 2^i
 * q[i, i + 2^j - 1] = min(q[i, i + 2^(j-1) - 1], q[i+2^(j-1), i+2^j-1])
 * {O(nlogn), o(1)}
 * 4. Segment trees
 * q[i, j] has left son q[i, (i+j)/2], q[(i+j)/1 + 1, j]
 * {O(n, logn}
 * 5. Least Common Ancestor (LCA)
 *   split tree into n^1/2 sections, each contains depth H^1/2 of the tree.
 *   Precompute P(x), depth first, O(N)
 *   {O(N), O(N^1/2)}
 * 6. LCA DP algorithm:
 *   P[i, j] is A[i]'s 2^jth ancestor
 *   P[i, 0] = T[i]
 *   P[i, j] = P[P[i, j-1], j-1]
 *   {O(NLogN), O(logN)}
 * 7. Reduce LCA to RMQ (restricted, adjcent elements diff by 1)
 *   Euler tour E[2N - 1], store nodes visited during euler tour. each edge visited twice,
 *   so the tour has exactly 2*N -2 edges, so 2*N-1 elments.
 *   Levels L[2N-1]
 *   Height H[N]
 *   LCA: min range query
 * 8. RMQ to LCA:
 *   Use cartisian tree of array
 * 9. Now that we know RMQ can be reduced to restricted RMQ, we can find a algorithm to solve it in
 *   {O(N), O(1)}
 *
 */
public class MinRangeQuery {
    /**
     * precompute all range queries q(i, j) in n square time using dynamic programming
     */
    public static void precompute() {

    }
}
