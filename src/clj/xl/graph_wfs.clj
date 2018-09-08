(ns xl.graph-wfs
  "1. all nodes considered white.
  2. If no more white node, done. Otherwise, pick one w, pendingNodes = [w], result = [w].
  3. If pendingNodes is empty, goto 2.
  3. for each of w in pendingNodes, remove w from pending nodes.
  4. find w's white adjacent nodes adjs, append adjs to result and pending nodes. mark adjs as gray.
  5.  mark w as black. goto 3.
  "
  (:require [clojure.set :as set])
  )

(defn wfs-node [node whites adj-map]
  (loop [pending [node]
        new-whites (disj whites node)
        result [node]]
    (if (empty? pending)
      result
      (let [n (first pending)
            white-adjs (filter new-whites (adj-map n))]
        (if (empty? white-adjs)
          (recur (rest pending) new-whites result)
          (recur (into (rest pending) white-adjs) (set/difference new-whites white-adjs) (into result white-adjs))
          )
        )

      )
    )
  )

(defn wfs [node-count adj-map]
  (loop [whites (set (range node-count))
        results []]
    (if (empty? whites)
      results

      (let [node (first whites)
            result (wfs-node node whites adj-map)
            new-whites (set (remove (set result) whites))]
        (recur new-whites (conj results result))
        )
      )
    )
  )

(defn do-main []
  (println (wfs 3 {}))
  (println (wfs 3 {1 #{1 2}}))
  (println (wfs 3 {0 #{2} 1 #{2} 2 #{0 1}}))
  (println (wfs 3 {0 #{1} 1 #{2} 2 #{0 1}}))
  (println (wfs 3 {0 #{1} 1 #{0} 2 #{0}}))
  (println (wfs 4 {0 #{1} 1 #{0 3} 2 #{0}}))
  (println (wfs 4 {0 #{0 1 2 3}}))
  )

;(do-main)