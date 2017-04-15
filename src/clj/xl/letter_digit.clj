(ns xl.letter-digit)

(defn digit-to-num [digit]
  (let [zero-num (int \0)
        x (int digit)
        ]
    (- x zero-num)
    )
  )

(defn num-to-letter [num]
  (let [a-digit (int \a)]
    (char (+ a-digit num))
    )
  )

(defn count-maps [digits index calculated]
  (if (neg? index)
    (calculated (count digits))
    (let [len (count digits)
          this-digit (nth digits index)
          this-num (digit-to-num this-digit)
          next-num (if (= index (dec len)) nil (digit-to-num (nth digits (inc index))))
          this-len (- len index)
          this-count
          (if
            (= index (dec len)) (if (= this-num 0) 0 1)
                                (cond
                                  (= this-num 0) 0
                                  (= this-num 1) (+ (calculated (dec this-len)) (calculated (- this-len 2)))
                                  (= this-num 2) (if (<= next-num 6)
                                                   (+ (calculated (dec this-len)) (calculated (- this-len 2)))
                                                   (calculated (dec this-len))
                                                   )
                                  :else (calculated (dec this-len))
                                  )
                                )
          ]
      (count-maps digits (dec index) (merge calculated {this-len this-count}))
      )
    )
  )

(comment

  (let [ss "102345"
        digits (vec ss)
        cnt (count-maps digits (dec (count digits)) {})
        ]
    (println "count of " ss " is " cnt)
    )
  )