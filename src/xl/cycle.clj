(ns xl.cycle)

(defn floyd [f x0 max-step]
  (let [x1 (f x0)
        x2 (f x1)]
    (cond
      (= x0 x1) x0
      (= x0 x2) x0
      :else
        (let [hare
              (loop [slow x1 fast x2 step-count 1]
                (println (str "slow: " slow ",false: " fast))
                (cond
                  (> step-count max-step) nil
                  (= slow fast) fast
                  :else (recur (f slow) (f (f fast)) (inc step-count))
                  )
                )
              tortoise x0
              mu (if (nil? hare)
                   nil
                   (loop [h hare t tortoise]
                     (if (= h t)
                       t
                       (recur (f h) (f t))
                       )
                     )
                   )
              ]
          mu
          )
      )
    )
  )

(let [f (fn [k]
          (if (< (inc k) 100) (inc k) (+ 100 (mod (inc k) 50)))
          )
      x0 0
      max-step 1000
      mu (floyd f x0 max-step)]
    (println "mu: " mu)
  )
