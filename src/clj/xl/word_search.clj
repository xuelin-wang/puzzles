(ns
  ^{:doc
    "
    Given a 2D board and a list of words from the dictionary, find all words in the board.

    Each word must be constructed from letters of sequentially adjacent cell, where ' adjacent ' cells are those horizontally or vertically neighboring.
     The same letter cell may not be used more than once in a word.

    For example,
    Given words = ['oath','pea','eat','rain'] and board =

    [
     ['o','a','a','n'],
     ['e','t','a','e'],
     ['i','h','k','r'],
     ['i','f','l','v']
     ]
    Return ['eat','oath'].
    Note:
    You may assume that all inputs are consist of lowercase letters a-z.

    Algo:
    walk the board,
    find all words for current prefix

    Walking:
    start from [0 0],
    for each starting cell:
    order: cell c (level 0), then all it's children (level 1).

    for level k: exhaust last node's children first. then advance to last nodes' next sibling.
    then back track one more level's next sibling...



    "
    }
  xl.word-search)

(defn trie [words]
  (let [new-node (fn [] {:value false})]
    (reduce
      (fn [root word]

        (let [{:keys [root prefix node]
               }
              (reduce
                (fn [{:keys [root prefix node] :as state} index]
                  (let [c (.charAt word index)
                        new-prefix (conj prefix c)
                        child (or (node c) (new-node))
                        ]
                    (assoc state :prefix new-prefix :node child :root (assoc-in root new-prefix child)))
                  )
                {:root root :prefix [] :node root}
                (range (count word))
                )
              ]
          (assoc-in root (conj prefix :value) true)
          )
        )
      (new-node)
      words

      )
    )
  )

(defn trie-samples []
  (doseq [words [["a" "ab" "cde" "cf"]]]
    (println (trie words))
    )
  )

;(trie-samples)

(defn valid? [x y m n]
  (and (nat-int? x) (nat-int? y) (< x m) (< y n))
  )

(defprotocol it
  (advance [this] "Go to next point, if nil, done")
  (get-path [this] "Get current path")
  )

(def deltas [[1 0] [0 1] [-1 0] [0 -1]])

(defn rest-deltas [dx dy]
  (let [idx (first (keep-indexed #(if (= %2 [dx dy]) %1) deltas))
        ]
    (vec (drop idx deltas))
    )
  )

(defn next-child [path m n]
  (let [point (last path)
        children (for [[dx dy] deltas] :let [[cx cy] [(+ x dx) (+ y dy)]] :when (and (valid? cx cy m n) (not (some #{[cx cy]} path)))
                   [cx cy]
                   )
        ]
    (first children)
    )
  )

(defn next-sibling [path m n]
  (let [[lx ly :as l] (last path)
        [px py :as p] (nth path (- (count path) 2))
        [dx dy] [(- lx px) (-ly py)]
        ds (rest-deltas dx dy)
        children (for [[dx dy] ds] :let [[cx cy] [(+ px dx) (+ py dy)]] :when (and (valid? cx cy m n) (not (some #{[cx cy]} path)))
                                       [cx cy]
                                       )]
    (first children)
    )
  )

(defn next-cell [x y m n]
  (if
    (= x (dec m)) (if (= y (dec n)) nil [0 (inc y)])
    [(inc x) y]
    )
  )


(deftype board-it [path m n]
  it
  (advance [this]

      )

  (get-path [this]
    @path
    )

  )

