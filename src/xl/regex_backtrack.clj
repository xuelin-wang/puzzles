(ns xl.regex-backtrack)

(defn to-pattern-items
  "Each pattern item can be: a char or '.' optionally followed by '*'"
  [pt]
  (let [pat-len (count pat)]
    (loop [results [] index 0]
      (cond
        (= index pat-len) results
        (or (= index (dec pat-len)) (!= (.chatAt pat (inc index)) \*))
          (recur (conj results (str (.charAt pat index))) (inc index))
        :else (recur (conj results (.substring pat index (+ index 2))) (+ index 2))
        )
      )
    )
  )

(defn mat-char [pat-ch ch]
  (or (= pat-ch \.) (= pat-ch ch))
  )

(defn mat-range [pat ss from-index]
  (let [pat-ch0 (first pat)
        ss-len (count ss)]
    (if (= (count pat) 1)
      (if (mat-char pat-ch0 (.charAt ss from-index))
        (let [index (inc from-index)] [index index])
        [-1 -1]
        )
      (loop [result [fromindex fromindex]]
        (let [[index0 index1] result
              next-index (inc index1)]
          (cond
            (= index1 ss-len) result
            (mat-char pat-ch0 (.charAt ss index1)) (recur [from-index next-index])
            :else result
            )
          )
        )
      )
    )
  )

(defn match
  "Algorithm:
  divide pattern into list of atomic patterns,
  each atomic pattern is either a char or '.' optionally followed by '*'.
  If last atomic pattern matches end of string. return true.
  Backtrack when:
    1. a non-last atomic pattern matches end of string.
    2. current atomic pattern can not be matched.
  Batchtrack operation:
    if stack is empty, return false.
    for last item in stack, [atomic pattern, match range, state],
    if state is not beginning of match range, decrease state, continue to next atomic pattern.
    otherwise, continue backtracking.
  "
  [pat s]
  (let [pats (to-pattern-items pat)
        pats-count (count pats)
        final-state (count s)
        ]
    (loop [matched-pats-state []]
      (cond
        (empty? matched-pats-state)
          (let [pat0 (first pats)
                [from-index to-index] (mat-range pat0 s 0)
                ]
            (if (neg? from-index)
              false
              (recur [[pat0 [from-index to-index] from-index]])
              )
            )



        )
      (if (and (= (count matched-pats) pats-count) (= state final-state))
          true
          (cond

            )
        )
      )
    )
  )
