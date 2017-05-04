(ns xl.word-break
  (:require [clojure.string])
  )
"
Given a non-empty string s and a dictionary wordDict containing a list of non-empty words,
add spaces in s to construct a sentence where each word is a valid dictionary word.
You may assume the dictionary does not contain duplicate words.

Return all such possible sentences.

For example, given
s = 'catsanddog',
dict = ['cat', 'cats', 'and', 'sand', 'dog'].

A solution is ['cats and dog', 'cat sand dog'].

UPDATE (2017/1/4):
The wordDict parameter had been changed to a list of strings (instead of a set of strings).
Please reload the code definition to get the latest changes.
"

(defn- get-next-word [{:keys [from to]} ss words]
  (let [len (count ss)]
    (if
      (= to len)
      nil
     (reduce
       (fn [_ end]
         (let [new-word (subs ss from end)]
           (if (words new-word) (reduced {:word new-word :from from :to end}) nil)
           )
         )
       nil
       (range (inc to) (inc len))
       )
     )
    )
  )

(defn sentences [ss dict]
  (let [words (set dict)
        len (count ss)]
    (loop [answers [] nodes [{:word "" :from 0 :to 0}]]
      (let [last-node (last nodes)
            rest-nodes (drop-last nodes)
            new-node (get-next-word last-node ss words)]
        (if (nil? new-node)
          (if (empty? rest-nodes)
            answers
            (recur answers rest-nodes)
            )
          (let [{:keys [word from to]} new-node
                new-path (concat rest-nodes [new-node])]
            (if (= to len)
              (recur (conj answers new-path)
                     new-path)
              (recur answers (concat new-path [{:from to :to to :word ""}]))
              )
            )
          )
        )
      )
    )
  )

(defn samples []
  (doseq [[ss words]
          [
           ["catsanddog" ["cat" "cats" "and" "sand" "dog"]]
           ]
          ]
    (println ss)
    (println words)
    (println "answers:")
    (doseq [answer (sentences ss words)]
      (println (map :word answer))
      )
    )
  )

;(samples)