(ns xl.power
  (:require [clojure.spec.alpha :as s]
            [clojure.spec.gen.alpha :as g]
            [clojure.test.check :as tc]
            [clojure.test.check.generators :as tcg]
            )
  )

(defn- bin-bits-accum [n accum]
    (if (< n 2)
        (conj accum n)
        (let [bit (mod n 2)
              next-n (int (/ n 2))]
          (bin-bits-accum next-n (conj accum bit))
          )
      )
  )

(defn bin-bits [n]
  (bin-bits-accum n [])
  )

(defn power-accum [bits accum curr-power]
  (if (empty? bits)
    accum
    (let [bit (first bits)
          new-curr-power (* curr-power curr-power)
          ]
        (power-accum (rest bits)
                     (if (zero? bit) accum (* accum curr-power))
                     new-curr-power
                     )

      )
    )
  )

(defn power [x n]
  (if (zero? x)
    (if (zero? n) 1 0)
    (let [bits (bin-bits n)]
      (power-accum bits 1 x)
      )
    )
  )

(comment
  (let [x 3 n 5]
    (println "Power " x " to " n " is: " (power x n))
    )
  )

(s/def ::base (s/with-gen number? #(s/gen (set (range -3 3)))))
(s/def ::exp (s/with-gen nat-int? #(s/gen (set (range 0 10)))))
(s/fdef power
        :args (s/cat :x ::base :n ::exp)
        :ret number?
        :fn (s/and #(<= (Math/abs (- (double (:ret %)) (Math/pow (-> % :args :x) (-> % :args :n)))) 0.000001)
                   ))

