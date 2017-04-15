(ns xl.agents
  (:import (java.util.concurrent SynchronousQueue)))

(defn relay [x i]
  (when (:next x)
    (send (:next x) relay i)
    )
  (when (and (zero? i) (:report-queue x))
    (.put (:report-queue x) i))
  x
  )



(defn run [m n]
  (let [q (new SynchronousQueue)
        hd (reduce (fn [next _] (agent {:next next}))
                   (agent {:report-queue q})
                   (range (dec m))
                   )
        ]
    (doseq [i (reverse (range n))]
      (send hd relay i)

      )
    (.take q)
    )
  )

(comment
  (time (run 1000 1000))
  )
