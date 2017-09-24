## Range minimum query and Least common ancestor
Use f(n) for precompute cost, g(n) for query cost

### Range minimum query
* Naive  \
Precompute all ranges.  \
f(n) = O(n^3), g(n) = O(1), space is O(n^2)

* Square root n  \
Precompute per square root n section.  \
f(n) = O(n), g(n) = O(n^0.5), space O(n^0.5)

* 2's power  \
For each i, precompute lengths of 2^k lengths. Query is to find two sections cover i to j and returns the min of them.  \
f(n) = O(nLogn), g(n) = O(1), space = O(nLogn)

* segment tree  \
Segment tree is a general approach. Each range i - j is represented as a
node, when i < j, children nodes are created for i to (i+j)/2 and (i+j)/2
to j.   \
f(n) = O(n), g(n) = o(logn)

### Least common ancestor
* Square root n  \
T(i) is the parent in the tree for node i. P(i) is the ancestor of i in top level of its section, unless i is at top level of its section, then P(i) is the T(i). dfs can calculate P(i) from T(i).  \
f(n): O(n), g(n): O(n^0.5), space: O(n)

* 2's power  \
For all nodes i, calculate i's ancestor at 2^j level above Level(i) for all j. When query, first find the ancestor of q's ancestor at p's level using the power ancestor data, assuming p's level is higher than q. Than use tree to find ancestor of p and q, using the precomputed data again.  \
f(n): O(nlogn), g(n): O(logn), space: O(nlogn).

### Reduction between LCA and RMQ
* LCA to RMQ  \
Use dfs to do a euler tour of the tree, need the following three arrays: E: order of visited nodes, L: level of nodes, H: index of first visits of nodes.  \
Time is O(n)

* RMQ to LCA  \
Cartesian tree: root if min node i, left child of node i is cartesian tree for range(0..i-1), right child is for range(i..n). Obvious that min query is LCA of the cartesian tree.  \
Construction using a stack in O(n): Loop through the nodes. When a node is visited, all nodes in the stack bigger than it will be removed and becomes right child of it.  \
Time is O(n)

* O(N, 1) algorithm  
partition restricted RMQ into blocks of size floor(logn/2), then use sparse tree (powers of 2) algorithm.
