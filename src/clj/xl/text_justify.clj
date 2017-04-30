(ns xl.text-justify
  [:require [clojure.string :as s]]
  )
"
Given an array of words and a length L, format the text such that each line has exactly L characters
and is fully (left and right) justified.

You should pack your words in a greedy approach; that is, pack as many words as you can in each line.
Pad extra spaces ' ' when necessary so that each line has exactly L characters.

Extra spaces between words should be distributed as evenly as possible.
If the number of spaces on a line do not divide evenly between words,
the empty slots on the left will be assigned more spaces than the slots on the right.

For the last line of text, it should be left justified and no extra space is inserted between words.

For example,
words: ['This', 'is', 'an', 'example', 'of', 'text', 'justification.']
L: 16.

Return the formatted lines as:
[
 'This    is    an',
 'example  of text',
 'justification.  '
 ]
Note: Each word is guaranteed not to exceed L in length.

"

(defn- take-words [words limit]
  (let [w (count words)]
    (reduce
      (fn [{:keys [index len] :as result} i]
        (let [word (nth words i)
              word-len (count word)
              space-len (if (zero? i) 0 1)
              new-len (+ len word-len space-len)]
          (if (> new-len limit)
            (reduced result)
            {:index i :len new-len}
            )
          )
        )
      {:index -1 :len 0}
      (range w)
      )
    )
  )

(defn- fill-spaces [words len-1space limit]
  (let [words-count (count words)
        extra-len (- limit len-1space)
        gap-count (dec (count words))
        min-extras (if (zero? gap-count) 0 (int (/ extra-len gap-count)))
        remaining-spaces (- extra-len (* min-extras gap-count))
        strs     (map-indexed
                   (fn [idx word]
                     (let [add-extra-space? (< idx remaining-spaces)
                           spaces-count (if
                                          (= idx (dec words-count))
                                          0
                                          (let [extra-spaces (inc min-extras)]
                                            (if add-extra-space? (inc extra-spaces) extra-spaces)
                                            )
                                          )
                           spaces (repeat spaces-count " ")
                           ]
                       (apply str word spaces)
                       )
                     )
                   words
                   )

        ]
    (apply str strs)
    )
  )

(defn justify [words limit]
  (loop [words words lines []]
    (if (empty? words)
      lines
      (let [{:keys [index len]} (take-words words limit)
            next-words-count (inc index)
            next-words (take next-words-count words)
            next-line (fill-spaces next-words len limit)
            ]
        (recur (drop next-words-count words) (conj lines next-line))
        )
      )
    )
  )

(defn- samples []
  (doseq [[words limit] [
                 [["This", "is", "an", "example", "of", "text", "justification."] 16]
                 ]]
    (println "words: " words)
    (println "limit: " limit)
    (let [lines (justify words limit)]
      (println (s/join "\n" lines))
      )
    ))

;(samples)
