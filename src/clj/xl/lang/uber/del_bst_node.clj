
"Given a root node reference of a BST and a key, delete the node with the given key in the BST.
Return the root node reference (possibly updated) of the BST.

Basically, the deletion can be divided into two stages:

Search for a node to remove.
If the node is found, delete the node.
Note: Time complexity should be O(height of tree).

Example:

root = [5,3,6,2,4,null,7]
key = 3

5
/ | .
3   6
/ |   |.
2   4   7

Given key to delete is 3. So we find the node with value 3 and delete it.

One valid answer is [5,4,6,2,null,null,7], shown in the following BST.

5
/ | .
4   6
/     | .
2       7

Another valid answer is [5,2,6,null,4,null,7].

5
/ | .
2   6
|   | .
4   7

"

(ns xl.lang.uber.del-bst-node
  )

(defn- del-tree-node [{:keys [left val right] :as root} val0]
  (if (nil? root)
    nil
    (let [res (compare val0 val)]
      (cond
        (zero? res)
        (cond
          (nil? left) right
          (nil? right) left
          :else (let [rval (:val right)
                 ]
              {:left left :right (del-tree-node right rval) :val rval}
            )
          )
        (neg? res)
          {:left (del-tree-node left val0) :right right :val val}
        (pos? res)
          {:left left :right (del-tree-node right val0) :val val}
        )
      )
    )
  )

(defn samples []
         (let [root {:val 5 :left {:val 3 :left {:val 2} :right {:val 4}} :right {:val 6 :right {:val 7}}}]
           (println (del-tree-node root 3))
           )
         )

(samples)