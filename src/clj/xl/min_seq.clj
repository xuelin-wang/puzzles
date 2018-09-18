(ns xl.min-seq
"
find minimum os sum of subarray
"
  )

(defn min-seq [nums]
  (:min0
    (reduce
      (fn [{:keys [min0 min1]} n]
        (let [new-min1 (min n (+ min1 n))]
          {:min0 (min min0 new-min1) :min1 new-min1}
          )
        )
      {:min0 (Long/MAX_VALUE) :min1 0}
      nums)
    )
  )

(defn do-min []
  (doseq [ss [
               [0]
               [1]
               [2 3 1 2 3 4]
               [-10 9 -5 -1 4 -8 12 -3 -7]
              [2 -2]
               ]]
          (println "seq: " ss)
          (println "min: " (min-seq ss))
          )
  )

;(do-min)
