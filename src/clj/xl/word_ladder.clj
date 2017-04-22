(ns xl.word-ladder)
"
Given two words (beginWord and endWord), and a dictionary's word list, find all shortest transformation sequence(s) from beginWord to endWord, such that:

Only one letter can be changed at a time
Each transformed word must exist in the word list. Note that beginWord is not a transformed word.
For example,

Given:
beginWord = 'hit'
endWord = 'cog'
wordList = ['hot','dot','dog','lot','log','cog']
Return
[
 ['hit','hot','dot','dog','cog'],
 ['hit','hot','lot','log','cog']
 ]
Note:
Return an empty list if there is no such transformation sequence.
All words have the same length.
All words contain only lowercase alphabetic characters.
You may assume no duplicates in the word list.
You may assume beginWord and endWord are non-empty and are not the same.
"

(defn- diff-1? [word1 word2]
  (=
    1
    (reduce
      (fn [diff-count i]
        (if (> diff-count 1)
          diff-count
          (if (= (nth word1 i) (nth word2 i)) diff-count (inc diff-count))
          )
        )
      0
      (range 0 (count word1))
      )
    )
  )

(defn- mkgraph [words]
  (let [len (count words)
        len-1 (dec len)
        edges
          (for [i (range len-1)
                j (range (inc i) len)
                ]
            (let [word1 (nth words i)
                  word2 (nth words j)]
              (if (diff-1? word1 word2)
                [word1 word2]
                nil
                )
              )
            )
        ]
      (reduce
        (fn [neighbours edge]
          (if (nil? edge)
            neighbours
            (let [[w1 w2] edge]
              (assoc neighbours w1 (conj (neighbours w1) w2) w2 (conj (neighbours w2) w1))
              )
            )
          )
        {}
        edges
        )
    )
  )

(defn- move-last-node [path]
  (let [[last-node last-nodes last-visited] (last path)
        new-last-node (first last-nodes)
        ]
      (conj (into [] (drop-last path)) [new-last-node (rest last-nodes) (conj last-visited new-last-node)])
    )
  )

(defn- add-answer [answer answers]
  (if (empty? answers)
    (conj answers answer)
    (let [len (count (first answers))
          new-len (count answer)]
      (cond
        (> new-len len) answers
        (= new-len len) (conj answers answer)
        (< new-len len) #{answer}
        )
      )
    ))

(defn ladder [from to words]
  (let [g (mkgraph words)]
    (loop [path [[from [] #{from}]] answers #{}]
      (let [[last-node last-nodes last-visited] (last path)
            next-nodes (remove last-visited (g last-node))
            ]
        (cond
          (and (not (empty? answers)) (>= (count path) (count (first answers)))) (recur (move-last-node (into []  (drop-last path)))  answers)
          (nil? last-node) (if (<= (count path) 2) answers (recur (move-last-node (into [] (drop-last path))) answers))
          (empty? next-nodes)
            (recur (move-last-node path) answers)
          :else
            (if (some #{to} next-nodes)
              (recur (move-last-node path) (add-answer (conj (into [] (map first path)) to) answers))
              (let [next-node (first next-nodes)]
                (recur (conj path [next-node (rest next-nodes) (conj last-visited next-node)]) answers)
                )
              )
        )
        )
      )
    )
  )

(defn samples []
  (let [[from to words] ["hit" "cog" ["hot" "dot" "dog" "lot" "log" "cog" "hit"]]]
    (println (ladder from to words))
    )
  )

;(samples)