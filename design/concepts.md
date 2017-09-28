# Concepts
## Machine learning
- Neural network, deep learning
- feedforward, feedbackwords, recurring, convolutionary
- linear regression, gradient descent
- softmax
- minimax

## Statistics
- markov chain, markov process
- monto carlo simulation,

## Algorithm    
### Turing Machine
An infinite cells of tape, initial position, state, a finite instruction table. Each step, read/write active cell, move left or right, may change to a new state, util arrive at one of an acceptable states.

Turing complete means being able to simulate any turing machine.

Halting problem: an algorithm f that can decide if a program p halts on input i.
h(p, i) = 1 | if p halts on i, 0|otherwise.

If such an Algorithm f exists, construct a partial function g:
g(i) = 1 | f(i, i) = 0, loop forever otherwise.

Say program for g is e. if f(e,e) is 0, then g(e) = 1, halts, so f(e, e) = 1. contradicts.

### NP, NP-complete
Optimization problem: find an optimal solution.

Decision problem: answer yes/no.

An optimization problem can be reduce to a decision problem if we use a bound k to check solution.

P: decision problems that can be solved in polynomial time.

NP: class of problems that can be verifed in polynomial time. Or equivalently, a problem can be solved in polynomial time by nondeterministic turing machine.

co-NP: class of problems that negation can be verified in polynomail time.

Don't know if P = NP, or NP = co-NP.

NPC: an NP problem is an NP-complete problem if every other NP can be reduced to it.

Some example NPC problems: SAT

Nondeterministic algorithm vs. Deterministic algorithm: Nondeterministic algorithm gives different output on different runs from same input. May be caused by randomized number, probabilitic mechanism, parallel race conditions etc. Nondeterministic automata.

### 21 NPC problems
- SAT or 3-SAT
- Integer programming or integer linear programming: maximize C'x where x satisfies Ax <= b and x >= 0, x in Zn.
- clique. Find a maximum clique, find cliques of size k
- set pack. Given U, S of subsets, find maximum number of subsets where no two share common elements. Or maximize sum of weights.
- vertex cover. Find maximum number of vertices such that every edge incident on at least one of the vertex.
- set cover. Smallest number of subsets in S where union of which equals U.
- feedback vertex set. for Graph (directed or undirected) G = (V,E), is there a set X such that |x| <= k and G-X is cycle free.
- feedback arc set. find minumum set of arcs in directed graph so that the graph is directed cycle free.
- Hamilton cycle. Does a hamilton cycle exists in a graph (directed or undirected)?
- vertex coloring. A graph is k-colorable? find the min chromatic number of coloring of a graph.
- Clique cover. Find min number of cliques in a graph such that all vertexes are covered.
- Exact cover. Given collection S containing subsets of X, find subcollections S* such that no two subcollections in S* share elements and S* cover X.
- Hitting set. Given collection C of subsets of a finite set S. find min S' as a subset of S such that S' contains at least one element for each collection in C.
- Steiner tree problem. Find a tree with min weight of a graph which contains specified subset of vertexes (terminals).
- 3-mimensional matching. T is subsets of X.Y.Z (cartesian product). Find max 3 dim matching.
- Knapsack problem. A set of items, each item has a weight and a value. Find max sum of values of items with sum of weight < k.
- Job sequencing. for jobs 1 to n, how to schedule them to m machines with minimum makespan (total length of time when all jobs are completed).
- Partition problem. Give a multiset S, whether we can partition S into S1 and S2 such that sum of items in S1 equals sum of items in S2.
- Max cut. Cut of vertexes of graph into two sets, such that number of edges between the two sets are maximal.

### Dijkstra, A* algorithm
### language types and grammars: context free, regex

## Formal system
- first order logic
- lambda calculus

## System architecture
- stream processing: watermark, windowing,...
- batch processing
- relational db
- nosql/new sql db
- CAP, Jepsen test
- Exactly once

## Popular systems    
- Map/Reduce, GFS, Big table
- Spanner database
- Kafka
- redis
- memcached
- Apache Storm
- onynx, snapshot marker

## functional language
### clojure vector implementation
### transducer
### monad, flatmap,
Monad in category: a monad is a eudofunctor T with two naturals: &eta;: 1c->T and &mu;: T(T)->T
- &mu; &#9900; T&mu; = &mu; &#9900; &mu;T
- &mu; &#9900; T&eta; = &mu; &#9900; &eta;T = 1T

Basically all sensible diagrams commutes.

Monad in programming: a monad is a wrapped type. Some functions on wrappeed type/wrapper type makes sense.
- Monad examples:  array, maybe, optional
- Functions applications:
    - map: unwrap, apply, then wrapped
    - flatmap: function signature is wrapped type to wrapper type. Results need be falttened.
