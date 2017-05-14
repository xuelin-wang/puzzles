(ns xl.LeastCommonAncestor)
"Algo 1: Assign a number to each node so that the binary tree is a search tree.
Then the search time is path length from root to the LCA.

"

(defn construct1 [{:keys [left right] :as root} min-val max-val]
  (if (nil? root)
    nil
    (let [mid-val (/ (+ min-val max-val) 2.0)
          left-result (construct1 left min-val mid-val)
          right-result (construct1 right mid-val max-val)
          ]
      {:node {:left (:node left-result) :right {:node right-result} :val mid-val}
       :node-val (merge {root mid-val} {:node-val left-result} {:node-val right-result})
       }
      )
    )
  )


