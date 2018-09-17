(ns xl.min-pivot-sort
  "Suppose an array sorted in ascending order is rotated at some pivot unknown to you beforehand.
  (i.e.,  [0,1,2,4,5,6,7] might become  [4,5,6,7,0,1,2]).
  Find the minimum element.

  Solution:
  Assume the sort is ascending!!
  Start at index, find out the direction at index and n/2 + index.
  /\\:found   \\/:found

  a// and b//: a > b: min must be middle. a<b: min must be side.
  a// and b\\: min must be side.
  a\\ and b//: min must be middle.
  a\\ and b\\: a > b: min is side. a < b: min is middle
  so total cost should be log n.

  Lesson:
  Add assumptions to simplify algorithms. And then check what is the cost to make the assumptions.

  "
  )

(comment
  (defn left [index n from to]
    (cond
      (= from to) from
      (< from to)
      (if (= from index) to (dec index))
      :else
      (case index
        0 (dec n)
        from to
        (dec index)
        )
      )
    )

  (defn right [index n from to]
    (cond
      (= from to) from
      (< from to)
      (if (= to index) from (inc index))
      :else
      (case index
        (dec n) 0
        to from
        (dec index)
        )
      )
    )

  (defn size [n from to]
    (inc (- (if (> from to) (+ to n) to) from))
    )

  (defn direction [index is-left nums from to]
    (let [n (count nums)
          target ((if is-left left right) n from to) ]
      (- (nth nums index) (nth nums target))
      )
    )


  )

(defn mid [n from to]
  (let [to1 (if (> from to) (+ to n) to)
        mid1 (long (/ (+ from to1) 2))
        ]
      (if (>= mid1 n) (- mid1 n) mid1)
    )
  )

(defn find-min [nums from to]
  (let [n (count nums)]
    (cond
      (= from to) from
      (or (= from (dec to)) (and (= from (dec n)) (zero? to)))  (min (nth nums from) (nth nums to))
      :else
        (let [m (mid n from to)
              im (nth nums m)
              ito (nth nums to)
              [new-from new-to]
                (if
                  (> ito im)
                    [from m]
                    [m to]
                  )]
          (find-min nums new-from new-to)
          )
      )
    )
  )

(defn rotate [n index]
  (into (vec (range index n)) (range index))
  )

(defn do-main []
  (doseq [pivot (range 5)]
    (let [nums (rotate 5 pivot)]
      (println (str "min in " nums " is " (find-min nums 0 4)))
      )
    )

  )

;(do-main)