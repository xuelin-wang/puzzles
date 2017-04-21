(ns xl.attendance)
"Given a positive integer n, return the number of all possible attendance records with length n,
which will be regarded as rewardable. The answer may be very large, return it after mod 109 + 7.

A student attendance record is a string that only contains the following three characters:

'A' : Absent.
'L' : Late.
'P' : Present.
A record is regarded as rewardable if it doesn't contain more than one 'A' (absent) or more than two continuous 'L' (late).

Example 1:
Input: n = 2
Output: 8
Explanation:
There are 8 records with length 2 will be regarded as rewardable:
'PP' , 'AP', 'PA', 'LP', 'PL', 'AL', 'LA', 'LL'
Only 'AA' won't be regarded as rewardable owing to more than one absent times.
Note: The value of n won't exceed 100,000.
"

(defn- find-rewardable [n data-n-1]
  (let [p-rewardable-1 (:p-rewardable data-n-1)
        a-rewardable-1 (:a-rewardable data-n-1)
        l-rewardable-1 (:l-rewardable data-n-1)
        ll-rewardable-1 (:ll-rewardable data-n-1)
        p-rewardable-1-no-a (:p-rewardable-no-a data-n-1)
        l-rewardable-1-no-a (:l-rewardable-no-a data-n-1)
        ll-rewardable-1-no-a (:ll-rewardable-no-a data-n-1)

        p-rewardable (+ p-rewardable-1 a-rewardable-1 l-rewardable-1 ll-rewardable-1)
        a-rewardable (+ p-rewardable-1-no-a l-rewardable-1-no-a ll-rewardable-1-no-a)
        l-rewardable (+ p-rewardable-1 a-rewardable-1)
        ll-rewardable l-rewardable-1
        p-rewardable-no-a (+ p-rewardable-1-no-a l-rewardable-1-no-a ll-rewardable-1-no-a)
        l-rewardable-no-a p-rewardable-1-no-a
        ll-rewardable-no-a l-rewardable-1-no-a
        ]
    {
     :p-rewardable p-rewardable
     :a-rewardable a-rewardable
     :l-rewardable l-rewardable
     :ll-rewardable ll-rewardable
     :p-rewardable-no-a p-rewardable-no-a
     :l-rewardable-no-a l-rewardable-no-a
     :ll-rewardable-no-a ll-rewardable-no-a
     }
    )
)

(defn rewardable [n]
  (reduce
    (fn [result k]
      (find-rewardable k result)
      )
    {
     :p-rewardable 1
     :a-rewardable 1
     :l-rewardable 1
     :ll-rewardable 0
     :p-rewardable-no-a 1
     :l-rewardable-no-a 1
     :ll-rewardable-no-a 0
     }
    (range 2 n)
    )
  )

(defn samples []
  (let [result (rewardable 3)]
    (println "result for 3:")
    (println result)
    )
  )

(samples)

;; (stest/check `di/max-div) hangs, why?
(s/fdef rewardable
        :args (s/cat :n pos-int?)
        :ret (s/map-of #{:p-rewardable
                         :a-rewardable
                         :l-rewardable
                         :ll-rewardable
                         :p-rewardable-no-a
                         :l-rewardable-no-a
                         :ll-rewardable-no-a
                         } nat-int?)
        :fn (s/and
              #(let [ret (:ret %)
                     n (-> % :args :n)
                     ]
                 (= n (apply + (vals ret)))
                 )
              ))

