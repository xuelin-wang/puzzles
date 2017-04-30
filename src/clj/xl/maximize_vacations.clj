(ns xl.maximize-vacations)
"
Rules and restrictions:
You can only travel among N cities, represented by indexes from 0 to N-1.
Initially, you are in the city indexed 0 on Monday.
The cities are connected by flights. The flights are represented as a N*N matrix (not necessary symmetrical),
called flights representing the airline status from the city i to the city j.
If there is no flight from the city i to the city j, flights[i][j] = 0; Otherwise, flights[i][j] = 1.
Also, flights[i][i] = 0 for all i.
You totally have K weeks (each week has 7 days) to travel.
You can only take flights at most once per day and can only take flights on each week's Monday morning.
Since flight time is so short, we don't consider the impact of flight time.
For each city, you can only have restricted vacation days in different weeks,
given an N*K matrix called days representing this relationship. For the value of days[i][j],
it represents the maximum days you could take vacation in the city i in the week j.
You're given the flights matrix and days matrix,
and you need to output the maximum vacation days you could take during K weeks.
"

(defn- next-city [from c n flights]
       (reduce
         (fn [_ ci]
             (if (or (= ci from) (pos? (-> flights (nth from) (nth ci))))
               (reduced ci)
               nil
               )
             )
         nil
         (range (inc c) n))
       )

(defn- init-cities [cs from-w n flights]
       (let [w (count cs)]
            (reduce
              (fn [cs wi]
                  (let [prev-c (if (zero? wi) 0 (nth cs (dec wi)))]
                       (assoc cs wi (next-city prev-c -1 n flights))
                       )
                  )
              cs
              (range from-w w)
              )
            )
       )

(defn- next-cities [cs n flights]
   (let [w (count cs)]
        (reduce
          (fn [_ wi]
              (let [c (nth cs wi)
                    prev-c (if (zero? wi) 0 (nth cs (dec wi)))
                    next-c (next-city prev-c c n flights)]
                   (if (nil? next-c)
                     nil
                     (reduced (init-cities (assoc cs wi next-c) (inc wi) n flights))
                     )
                   )
              )
          nil
          (reverse (range w))
          )
      )
   )

(defn- calc-days [cs days]
       (apply + (map-indexed (fn [idx c] (-> days (nth c) (nth idx))) cs))
       )

(defn possible-cities
      ([n w flights] (possible-cities n w (into [] (repeat w 0 )) flights))
      ([n w cs flights] (lazy-seq (cons cs (possible-cities n w (next-cities cs n flights) flights))))
      )

(defn maximize [flights days]
      (let [n (count (first flights))
            w (count (first days))]
           (reduce
             (fn [m cs]
                 (if (nil? cs)
                   (reduced m)
(max (calc-days cs days) m)                               )
                 )
             0
             (possible-cities n w flights)
             )
           )
  )

(defn samples []
      (doseq [x
           [
            [
             [[0,1,1],[1,0,1],[1,1,0]] [[7,0,0],[0,7,0],[0,0,7]]
             ]

            ]
            ]
        (let [[flights days] x]
          (println "flights: " flights)
          (println "days: " days)
          (println "results: " (maximize flights days))
          )
        )
      )

;(samples)