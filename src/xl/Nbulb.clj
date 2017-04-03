(ns xl.Nbulb)

(defn switch-on
  "Return sequence of bulb to turn the switches"
  [s0]
  (loop [results [] remaining s0 even-times? true]
    (if (empty? remaining)
      results
      (let [next-index
            (first (keep-indexed
                     (fn [idx item]
                       (if (or (and even-times? (not item)) (and (not even-times?) item))
                         idx nil))
                     remaining
                     ))
            last-index (if (empty? results) -1 (last results))
            ]
        (if (nil? next-index)
          results
          (recur (conj results (+ last-index 1 next-index)) (drop (inc next-index) remaining) (not even-times?))
          )
        )
      )
    )
  )

(let [states [[] [true] [false]
             [true true] [true false] [false false] [false true]
             [true false true false false true true false true false false]
             ]
      switches (map  (fn [state] [state (reverse (switch-on state))])  states)
      ]
  (dorun
    (map
      (fn [[state switches]]
        (println "state: " state)
        (println "switches: " switches)
        )
      switches)
    )
  )