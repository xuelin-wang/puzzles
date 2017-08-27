# algoritms

Playground for small clojure/java programs.
Some of the programs:
* Sudoku solution using Dancing links

# Algorithms list
* kth smallest element in unsorted array
    * quick select. Worst O(nLogn), average O(n)
    * min heap. n + kLogn
    * max heap. nLogk
    * naive sort first: nlogN

* kth largest in multiplication table:
    * Use kth largest in above algorihm. Plus calc the m * n table!
    * Binary search: count numbers <= k in mn table. starting from (1 + mn) / 2. m * logn

* beautiful arrangement: given n, k, arange 1 to n in array a1 to an, so that abs(ai - a[i+1]) has k different numbers.
    * k = 1: 1..n. k = 2: 2..n, 1. Greedy: for i = k-1..1, let j = k - i. current arrary is a[1..j-1], new a[j] = a[j-1] +- i.

## License

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
