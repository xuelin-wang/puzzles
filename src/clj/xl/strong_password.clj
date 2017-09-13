(ns
  ^{:doc "

A password is considered strong if below conditions are all met:
It has at least 6 characters and at most 20 characters.
It must contain at least one lowercase letter, at least one uppercase letter,
and at least one digit.\nIt must NOT contain three repeating characters in a row ('...aaa...' is weak, but '...aa...a...' is strong,
assuming other conditions are met).\nWrite a function strongPasswordChecker(s),
that takes a string s as input, and return the MINIMUM change required to make s a strong password.
If s is already strong, return 0.\n\nInsertion, deletion or replace of any one character are all considered as one change.

  "}
  xl.strong-password)

(defn details [pw]
  (let [check-run (fn [run]
                  (let [{:keys [char cnt idx]} run
                        ]
                    (when (> cnt 2) {:begin idx :char char :cnt cnt} )
                    )
                  )
        res
        (reduce
          (fn [{:keys [lower upper digit runs run index]} ch]
            (let [c (int ch)
                  dl (if (<= (int \a) c (int \z)) 1 0)
                  du (if (<= (int \A) c (int \Z)) 1 0)
                  dd (if (<= (int \0) c (int \9)) 1 0)
                  {:keys [char cnt idx]} run
                  run-cnt (if (= ch char) (inc cnt) 1)
                  new-index (inc index)
                  run-idx (if (= ch char) idx new-index)
                  dr (when-not (= ch char) (check-run run))
                  ]
              {:lower (+ lower dl) :upper (+ upper du) :digit (+ digit dd)
               :runs (if (nil? dr) runs (conj runs dr))
               :run {:char ch :cnt run-cnt :idx run-idx}
               :index new-index}
              )
            )
          {:lower 0 :upper 0 :digit 0 :runs [] :run {:char nil :cnt 0 :idx -1} :index -1}
          (seq pw))
        dr (check-run (:run res))
        ]
    (if (nil? dr) res (merge res {:runs (conj (:runs res) dr)}))
    )
  )

(defn check-pw [pw]
  (let [{:keys [lower upper digit runs]} (details pw)
        l (count pw)
        add (max 0 (- 6 l))
        del (max 0 (- l 20))
        to-add (if (zero? lower) 1 0)
        to-add (if (zero? upper) (inc to-add) to-add)
        to-add (if (zero? digit) (inc to-add) to-add)
        to-add (if (>= add to-add) 0 (- to-add add))        ;; when add < to-add: to-add=1, add = 0 || to-add = 2, add in (0, 1)

        ]
    (cond
      (pos? add)
      ;add into the best run(s),
      ; if a run is length r, will need r/3 updates to break it up.
      ; add 1 to 3k+2 doesn't reduce updates in run. should add to 3k+1, if no such runs, then 3k.
      ; if to-add is positive, do same thing for to-add.
      ; after that, if:
      ; 1. still add or to-add left, means no more runs, answer will be add + to-add
      ; 2. still runs left, means no more add or to-add, calculate updates needed to fix runs. sum(Ri/3)
      (pos? del)
      ; delete from run with length 3k, then 3k+1, then 3k+2
      ; if to-add is positive, process similar as above.
      ; 2. if still del left, no more runs. return del + to-add
      ; 3. if still runs left, means no more del to to-add, calculate updates needed to fix runs.

      )

    )
  )

(defn details-samples []
  (doseq [pw ["" "a" "b" "Z" "C" "1" "0" "aa" "AA" "a0" "ab" "aa" "aaa" "a0C" "abb" "aba" "aaaa" "aaab" "aaabcccc" "Baaaa0bb333AAAAA4fff"]]
    (println (str "details for password " pw " is: "))
    (println (details pw))
    )
  )

;(details-samples)