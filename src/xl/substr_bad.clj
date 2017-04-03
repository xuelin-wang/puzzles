(ns xl.substr-bad

  (:require [clojure.string]))

(defn longgest-with [ss index left?]
  (let [limit (if left? 0 (dec (count ss)))
        boundary
          (loop [boundary index]
               (cond
                 (= boundary limit) boundary
                 (nat-int? (.indexOf (.substring ss (if left? boundary index) (inc (if left? index boundary)))
                                 (int (.charAt ss (if left? (dec boundary) (inc boundary))))
                                 )) boundary
                 :else (recur (if left? (dec boundary) (inc boundary)))
                 )
               )
        ]
    boundary
    )
  )

(defn longest-nonrepeating [ss]
  (cond
    (clojure.string/blank? ss) ""
    (= 1 (count ss)) ss
    :else
      (let [len (count ss)
            mid (int (/ len 2))
            left (.substring ss 0 mid)
            left-str (longest-nonrepeating left)
            left-len (count left-str)
            right (.substring ss (inc mid))
            right-str (longest-nonrepeating right)
            right-len (count right-str)
            mid-left (longgest-with ss mid true)
            mid-right (longgest-with ss mid false)
            mid-str (.substring ss mid-left (inc mid-right))
            mid-len (count mid-str)
            max-len (max left-len right-len mid-len)
            ]
        (cond
          (= max-len left-len) left-str
          (= max-len mid-len) mid-str
          :else right-str
          )
        )
    )
  )

(doseq [ss ["" "a" "ab" "aa" "abc" "aba" "aab" "abb" "aaa" "abcd" "abcb" "abca" "abbc"]]
  (println ss " has substr: " (longest-nonrepeating ss))
  )

(println (longgest-with "aabcdc" 2 true))