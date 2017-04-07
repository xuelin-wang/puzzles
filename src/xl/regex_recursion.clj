(ns xl.regex-recursion)

(defn mat [pat ii ss jj]
  (let [pat-len (count pat)
        ss-len (count ss)]
    (cond
      (= ii pat-len) (= jj ss-len)
      (= jj ss-len) false
      :else (let [pat-ch (nth pat ii)
            with-star (and (< ii (dec pat-len)) (= \* (nth pat (inc ii))))
            ss-ch (nth ss jj)
            match-first (or (= pat-ch ss-ch) (= pat-ch \.))
            ]
          (if with-star
            (let [case-zero (mat pat (+ ii 2) ss jj)
                  ]
              (if match-first
                (or case-zero (mat pat (+ ii 2) ss (inc jj)) (mat pat ii ss (inc jj)))
                case-zero
                )
              )
            (if match-first (mat pat (inc ii) ss (inc jj)) false)
            )
        )
      )
    )
  )

(defn match [pat0 ss0]
  (let [pat (seq pat0)
        pat-len (count pat)
        ss (seq ss0)
        ss-len (count ss)
        ]
      (mat pat 0 ss 0)
    )
  )

(doseq [ss-pat [
                ["a" "aa"]
                ["aa" "aa"]
                ["aaa" "aa"]
                ["aa" "a*"]
                ["aa" ".*"]
                ["ab" ".*"]
                ["aab" "c*a*b"]
                ]]
  (let [[ss pat] ss-pat]
    (println "pat:" pat ", ss:" ss)
    (println "matched: " (match pat ss))
    )
  )
