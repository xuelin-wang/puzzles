(ns xl.bin-tree-serializer
  "
  Serialization is the process of converting a data structure or object into a sequence of bits so that it can be store
  d in a file or memory buffer, or transmitted across a network connection link to be reconstructed later in the same or another computer environment.

  Design an algorithm to serialize and deserialize a binary tree. There is no restriction on how your serialization/deserialization algorithm should work.
  You just need to ensure that a binary tree can be serialized to a string and this string can be deserialized to the original tree structure.

  Example:

  You may serialize the following tree:

      1
     / \\
    2   3
       / \\
      4   5

  as '[1,2,3,null,null,4,5]'
  Clarification: The above format is the same as how LeetCode serializes a binary tree. You do not necessarily need to follow this format,
   so please be creative and come up with different approaches yourself.

  Note: Do not use class member/global/static variables to store states. Your serialize and deserialize algorithms should be stateless.

  Solution:
  A:
  dfs or wfs:
  Time/space: O(n).
  "
    )

(defn ser [tree]
  (let [[root left right] tree]
    (if (nil? root)
      [:null]
      (into [] (concat [root] (ser left) (ser right)))
      )
    )
  )

(defn pop-tree [ss]
  (let [[r & rest] ss]
    (if (= :null r)
      [nil rest]
      (let [[l-tree r1] (pop-tree (vec rest))
            [r-tree r2] (pop-tree r1)]
        [[r l-tree r-tree] r2]
        )
      )
    )
  )

(defn deser [ss]
  (let [[tree rest] (pop-tree ss)]
    (if (empty? rest)
      tree
      (throw (IllegalArgumentException. (str "Invalid deser. data=" ss))))
    )
  )

(defn do-ser []
  (println "here")
  (doseq [tree [
                [0 [1] nil]
                ;[1 [2] [3 [4] [5]]]
                   ]]
    (println "tree: " tree)
    (println "ser: " (ser tree))
  )
)

(defn do-deser []
  (println "here")
  (doseq [ss [
              [0 1 :null :null :null]
              ;              [-1 :null 9 :null :null]
              ;                [1 2 :null :null 3 4 :null :null 5 :null :null]
                ]]
    (println "ss: " ss)
    (println "tree: " (deser ss))
    )
  )

;(do-ser)
;(do-deser)