(ns xl.count-swaps)

(defn count-swaps [n]
  (let [a (atom 0)
        begin-millis (System/currentTimeMillis)
        ;;        tmp (take n (repeatedly #(swap! a inc)))
        tmp (map (fn [_] (swap! a inc)) (range 0 n))
        _ (dorun tmp)
        end-millis (System/currentTimeMillis)
        ]
      (println "time for " n "swaps took: " (- end-millis begin-millis) "ms")
    )
  )

(comment
  (count-swaps 1000000)
  )
