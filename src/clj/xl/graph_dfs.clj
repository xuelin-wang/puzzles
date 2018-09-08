(ns xl.graph-dfs
  "Recursive:
  1. All nodes are white by default.
  2. Pick any white node n.
  3. dfs(n).
  4. Mark n as black. go to 2.

  dfs(n):
  1. Mark n as gray.
  2. If n does not have white children (or adjacent node), done. Otherwise pick a white child(adjacent node) c.
  3. dfs(c). Go to 2.

  Non-recursive:
  1. All nodes are white by default. current path p is empty.
  2. If no more white node, done. otherwise pick any white node n, mark as gray. set p = [n].
  3. If p is empty, go to 2. otherwise, take last element m from p.
  4. if m has no white adjacent node, mark m as black. remove it form p. go to 3.
  5. pick m's white adjacent node x, mark as gray, append to p. go to 3.
    "
  (:require [clojure.set :as set])
  )

(defn dfs-node [node whites adj-map]
  (loop [path [node]
         result [node]
         curr-whites (disj whites node)]
    (if (empty? path)
      result
      (let [curr-node (last path)
            white-adj (first (filter curr-whites (adj-map curr-node)))]
        (if white-adj
          (recur (conj path white-adj) (conj result white-adj) (disj curr-whites white-adj))
          (recur (drop-last path) result curr-whites)
          )
        )
      )
    )
  )

(defn dfs [nodeCount adj-map]
  (loop [whites (set (range 0 nodeCount))
         results []
         ]
    (if (empty? whites)
      results
      (let [result (dfs-node (first whites) whites adj-map)
            new-whites (set (remove (set result) whites))
            new-results (conj results result)]
        (recur new-whites new-results)
        )
      )
    )
  )

(defn do-main []
  (println (dfs 3 {}))
  (println (dfs 3 {1 #{1 2}}))
  (println (dfs 3 {0 #{2} 1 #{2} 2 #{0 1}}))
  (println (dfs 3 {0 #{1} 1 #{2} 2 #{0 1}}))
  (println (dfs 3 {0 #{1} 1 #{0} 2 #{0}}))
  (println (dfs 4 {0 #{1} 1 #{0 3} 2 #{0}}))
  )

(do-main)