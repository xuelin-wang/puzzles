
(ns
  ^{:doc "
    You are given a string, s, and a list of words, words, that are all of the same length.
    Find all starting indices of substring(s) in s that is a concatenation of each word in words exactly once
    and without any intervening characters.

    For example, given:
    s: 'barfoothefoobarman'
    words: ['foo', 'bar']

    You should return the indices: [0,9].
           (order does not matter).

    Solution:
      for i from 0 to word length -1:
        scanning words aligned by i, looking for matched words list.
        advancing left when necessary.
    Time: O(n)
    "
    }
  xl.substring-with-concatenation-of-all-words)

(defn find-indices [s words]
  (let [wl (count (first words))
        word-count (count words)
        sl (count s)]
    (reduce
      (fn [results rem]
        (reduce
          (fn [results m]
            (let [left (:left results)
                  next-word-end (+ left (* (inc m) wl))]
              (if (> next-word-end wl)
                (reduced results)
                (let [next-word-begin (+ left (* m wl))
                      next-word (.substring s next-word-begin next-word-end)
                      word-index (find-word next-word words)
                      matched (:matched results)]
                  (cond
                    (neg? word-index) (assoc results :left next-word-end :matched {})
                    (matched word-index)
                      (let [
                            prev-index (get matched word-index)
                            new-matched (->> matched (remove #(<= (second %) prev-index)) (into {}))
                            new-matched (assoc new-matched word-index next-word-begin)
                            ]
                        (assoc results
                          :left (+ word-index wl)
                          :matched new-matched
                          ))
                    :else
                      (if (= word-count (dec (count matched)))
                        (let [new-matched (remove #(= left (second %)) matched)
                              new-matched (assoc new-matched word-index next-word-begin)]
                          (assoc results :left (+ left wl) :matched new-matched :indices (conj (:indices result) left))
                          )
                        )
                    )
                  )
                )
              )
            )
          (assoc results :left 0 :matched {})
          (range (/ sl wl))
          )
        )
      {:indices []}
      (range wl)
      )
    )
  )

