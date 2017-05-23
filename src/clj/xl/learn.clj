(ns xl.learn
  (:require [clojure.string]
            [clojure.pprint :refer [pprint]]
            [clojure.spec.alpha :as s]
            [clojure.core.async :as a :refer [>! <! >!! <!! go go-loop chan buffer close! thread
                                              alts! alts!! timeout]]
            )
  )

(defn pascal [c r]
  (if (or (zero? c) (= c r))
    1
    (+ (pascal (dec c) (dec r)) (pascal c (dec r)))
    )
  )

(defn- check-paren [chars left-paren-count]
  (cond
    (neg? left-paren-count) false
    (empty? chars) (zero? left-paren-count)
    :else (let [next-char (first chars)]
      (cond
        (= next-char \( ) (check-paren (rest chars) (inc left-paren-count))
        (= next-char \) ) (check-paren (rest chars) (dec left-paren-count))
        :else (check-paren (rest chars) left-paren-count)
        )
      )
    )
  )


(defn bal-paren [chars] (check-paren chars 0))

(defn- get-changes [money coins curr-changes]
  (cond
    (= money 0) curr-changes
    (empty? coins) nil
    :else (let [next-coin (first coins)
                next-changes (range 0 (inc (quot money next-coin)))
                changes  (apply concat
                           (filter not-empty
                                   (map
                                     (fn [next-change]
                                       (let [new-changes (map #(assoc % next-coin next-change) curr-changes)]
                                         (get-changes (- money (* next-change next-coin)) (rest coins) new-changes)))
                                     next-changes)
                                   )
                           )
                ]
            changes))
  )

(defn changes [money coins]
  (get-changes money coins [{}]))

(defn count-changes [money coins] (count (changes money coins)))


(defn fib [n]
  (reduce
    (fn [[pp p] _]
      [p (+ pp p)])
    [0 1]
    (range 1 n))
  )

(defn metrics [chan-count batch]
  (let [chans (doall (map (fn [_] (chan)) (range chan-count)))]
    (go-loop []
             (Thread/sleep 1000)
             (doseq [index (range chan-count)] (>! (nth chans index) (rand (inc index)) ))
             (recur)
             )

    (loop [totals (repeat chan-count 0.0) batch-count 0]
      (Thread/sleep 1500)
      (if (> batch-count batch)
        nil
        (let [nexts (loop [index 0 accum []]
                      (if (= index chan-count)
                        accum
                        (let [n (<!! (nth chans index))] (recur (inc index) (conj accum n)))
                        ))

              _ (println (str nexts))
              new-totals (map + totals nexts)
              agg_totals (apply + new-totals)
              new-count (inc batch-count)
              avgs (map #(/ % new-count) new-totals)
              ]
          (println (str "batch: " new-count " totals: " (pr-str new-totals)))
          (println (str "        averages: " (pr-str avgs)))
          (println (str "aggregated total: " agg_totals))
          (println (str "aggregated average: " (/ agg_totals new-count)))
          (recur new-totals new-count)
          )

                            )
      )
    ))


;(changes 32 [10 5 2 1])

(comment
  (let [
        ll
          (map (fn [i] (map #(xl.learn/pascal % i) (range 0 (inc i))))
             (range 0 4))
        output (->> ll
                    (map (fn [ll] (clojure.string/join " " (conj ll "\n"))))
                    (apply str)
                 )
        ]
    (println output)
    )
  )
