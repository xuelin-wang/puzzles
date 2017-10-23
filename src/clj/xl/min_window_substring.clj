"
Given a string S and a string T, find the minimum window in S which will contain all the characters in T in complexity O(n).

For example,
S = 'ADOBECODEBANC'
T = 'ABC'
Minimum window is 'BANC'.

Note:
If there is no such window in S that covers all characters in T, return the empty string "".

Analysis:
scan S,
whenever see char in T, update last index of the char to current index.
When all T's char has last index, compare the current window
(min of last indexes to max of last indexes) to current min window.
Then remove min last index, continue.
"

(ns xl.min-window-substring)

(defn- win-len [[begin end]]
  (- end begin)
  )

(defn find-win [s t]
  (let [chs (set (seq t))
        m (count chs)
        n (.length s)
        {:keys [indexes min-win]}
        (reduce
          (fn [{:keys [indexes min-win]} index]
            (let [ch (.charAt s index)
                  indexes (if (chs ch) (assoc indexes ch index) indexes)
                  ]
                (if (= m (count indexes))
                  (let [first-ch (apply min-key indexes chs)
                        first-index (indexes first-ch)
                        min-win (if (or (nil? min-win) (< (win-len [first-index index]) (win-len min-win)))
                                  [first-index index]
                                  min-win)
                        indexes (dissoc indexes first-ch)
                        ]
                      {:indexes indexes :min-win min-win}
                    )
                  {:indexes indexes :min-win min-win}
                  )
              )
            )
          {:indexes {} :min-win nil}
          (range n)
          )
        ]
    (let [[begin end] min-win]
      (if (nil? begin) ""
                       (.substring s begin (inc end)))
      )
    )
  )

(defn samples []
  (doseq [[s t]
          [
           ["ADOBECODEBANC" "ABC"]
           ]
          ]
    (println (str "S: " s "\n T: " t "\n" "Min: " (find-win s t)))
    )
  )

(samples)
