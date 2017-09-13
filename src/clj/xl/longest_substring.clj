(ns xl.longest-substring)
"Find longest substrings of k strings.
Dynamic programming algorithm takes sum(lengths) space and prod(lengths) time.
Takes n max(length) time using suffix tree"

(defn dynamic-algo
  "Find longest suffix for all prefix pairs of two strings.
  p(i, j): if s[i-1] = t[j-1] then p(i-1, j-1) + 1, else 0"
  [s1 s2]
  (let [[l1 l2] (map count [s1 s2])
        [l1-1 l2-1] (map inc [l1 l2])
        answer-table (make-array Object l1-1 l2-1)
        empty-answer {:len 0 :ss-end 0}]
    ; init answer-table[0][r], answer-table[r][0] to 0
    (doseq [ii (range l1-1)]
      (aset answer-table ii 0 empty-answer)
      )
    (doseq [ii (range l2-1)]
      (aset answer-table 0 ii empty-answer)
      )

    (doall
      (for [ii (range 1 l1-1) jj (range 1 l2-1)]
        (let [{:keys [len ss-end]} (aget answer-table (dec ii) (dec jj))
              s1-ii (get s1 (dec ii))]
          (aset answer-table ii jj
                (if (= s1-ii (get s2 (dec jj)))
                  {:len (inc len) :ss-end ii}
                  empty-answer
                  )
                  )
          )
        )
      )
    (let [{:keys [len ss-end]} (apply (partial max-key :len) (flatten (mapv vec answer-table)))]
      (if (zero? len) "" (subs s1 (- ss-end len) ss-end))
      )
    )
  )

(defn- dynamic-samples []
  (doseq [[s1 s2]
            [
             ["" ""]
             ["a" "b"]
             ["abc" "bcd"]
             ]
          ]
    (println "s1: " s1)
    (println "s2: " s2)
    (println (dynamic-algo s1 s2))
    )
  )

;(dynamic-samples)