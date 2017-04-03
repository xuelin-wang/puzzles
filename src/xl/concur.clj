(ns xl.concur)

(def ft (future (Thread/sleep 1000) "ft"))

(def dl (delay "dl"))

(def pp1 (promise))

(deliver pp1 10)

(defmacro wait [timeout & body]
`  (do (Thread/sleep ~timeout)
      ~@body
    )
  )

(defn relay [x i]
  (when (:next x)
    (send (:next x) relay i)
    )
  (when (and (zero? i) (:report-queue x))
    (.put (:report-queue x) i)
    )
  x
  )

(defn run [m n]
  (let [q (new java.util.concurrent.SynchronousQueue)
        hd (reduce (fn [next _] (agent {:next next}))
                   (agent {:report-queue q})
                   (range (dec m)))
        ]
    (doseq [i (reverse (range n))]
      (send hd relay i)
      )
    )
  )

;(time (run 1000 1000))

(defprotocol AProtocol
  "a test protocol"
  (bar [a b] "bar!")
  (baz [a] [a b] [a b c] "baz 1 2 3")
  )
