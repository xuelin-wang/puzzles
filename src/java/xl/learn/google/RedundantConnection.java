package xl.learn.google;

/**
 * Created by xuelin on 9/27/17.
 * In this problem, a rooted tree is a directed graph such that, there is exactly one node (the root) for which all other nodes are descendants of this node, plus every node has exactly one parent, except for the root node which has no parents.

 The given input is a directed graph that started as a rooted tree with N nodes (with distinct values 1, 2, ..., N), with one additional directed edge added. The added edge has two different vertices chosen from 1 to N, and was not an edge that already existed.

 The resulting graph is given as a 2D-array of edges. Each element of edges is a pair [u, v] that represents a directed edge connecting nodes u and v, where u is a parent of child v.

 Return an edge that can be removed so that the resulting graph is a rooted tree of N nodes. If there are multiple answers, return the answer that occurs last in the given 2D-array.

 Example 1:
 Input: [[1,2], [1,3], [2,3]]
 Output: [2,3]
 Explanation: The given directed graph will be like this:
 1
 / \
 v   v
 2-->3
 Example 2:
 Input: [[1,2], [2,3], [3,4], [4,1], [1,5]]
 Output: [4,1]
 Explanation: The given directed graph will be like this:
 5 <- 1 -> 2
 ^    |
 |    v
 4 <- 3
 Note:
 The size of the input 2D-array will be between 3 and 1000.
 Every integer represented in the 2D-array will be between 1 and N, where N is the size of the input array.


 Solution:
 case 1: If the added edge u->v and v != root, then: v now has two parents, so must remove an edge x->v.
 case 2: if the added edge is u->root r, now every node has one parent. The existing unique path from r to u and the new edge form a circle.
 can cut any edge of the circle.
 scanning all edges, keep track of nodes' incoming edges. also check if a node has two incoming edges.
 for case 1, simply removing last edge of the node with incoming degree of 2.
 for case 2, need detect circle. circle happens for edge u->v, when there is already a path from v to u. which means u is an ancestor of v before. We can find out
 by tracing parent node from v, check if we can see u. When circle detected. We remove the edge which trigger the circle.
so we construct two maps. pi for parent, deg for incoming degrees.
 For pi function, a more efficient implementation maybe using union find. Each node remember the root  of subtree discovered so far.

 Simplified:
 for new edge u->v, if p[v] != v, this is the edge needs be removed.
 otherwise, if p[v] = v, set p[v] = p[u].
 Cost: O(n). space: O(n).
 */
public class RedundantConnection {
}
