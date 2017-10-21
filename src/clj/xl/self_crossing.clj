"
ou are given an array x of n positive numbers. You start at point (0,0) and moves x[0] metres to the north,
 then x[1] metres to the west, x[2] metres to the south, x[3] metres to the east and so on.
 In other words, after each move your direction changes counter-clockwise.

Write a one-pass algorithm with O(1) extra space to determine, if your path crosses itself, or not.

Example 1:
Given x =
[2, 1, 1, 2]
,
?????
?   ?
???????>
?

Return true (self crossing)
Example 2:
Given x =
[1, 2, 3, 4]
,
????????
?      ?
?
?
?????????????>

Return false (not self crossing)
Example 3:
Given x =
[1, 1, 1, 1]
,
?????
?   ?
?????>

Return true (self crossing)

Analysis:

consider if there is an intersection, assume the first intersection X,
X can be:
1. vertical up crossing horizontal: v is the third step after crossed line.
2. vertical down crossing horizontal: v is the fifth step after crossed line.
3. horizontal right crossing vertical: h is the third step after crossed.
4. horizontal left crossing vertical: h is the fifth step after crossed.

So, for each horizontal/vertical step, just need check the third/fifth step cross it.
Each check takes constant time. so O(n) time. space is similar.

"
(ns xl.self-crossing)

(def direction-factor
  [
   [0 1]
   [-1 0]
   [0 -1]
   [1 0]
   ]
  )

(defn- get-end-point [[start-x start-y] direction len]
  (let [[factor-x factor-y] (nth direction-factor direction)
        end-x (+ start-x (* factor-x len))
        end-y (+ start-y (* factor-y len))])
  [end-x end-y]
  )

(defn- nth-end-point [start-point direction step step-delta lens]
  (reduce
    (fn [start-point delta]
      (let [next-step (+ step delta)
            step-len (nth lens next-step)]
        (get-end-point start-point (mod (+ direction (inc delta)) 4) step-len)
        )
      )
    start-point
    (range (inc step-delta)))
  )

(defn- line-cross [[[x11 y11] [x12 y12]] [[x21 y21] [x22 y22]]]
  (if (= x11 x12)
    (and (or (< y11 y21 y12) (< y12 y21 y11)) (or (< x21 x11 x22) (< x22 x11 x21)))
    (and (or (< x11 x21 x12) (< x12 x21 x11)) (or (< y21 y11 y22) (< y22 y11 y21)))
    )
  )

(defn crossing? [step-lens]
  (let [n (count step-lens)]
    (reduce
      (fn [{:keys [start direction]} step]
        (let [end (nth-end-point start direction step 0 step-lens)]
          (if (>= (+ step 3) n)
            (reduced false)
            (let [end3-start (nth-end-point start direction )])
            )

          )
        (let [len (nth step-lens step)])
        )
      {:start [0, 0] :direction 0}
      (range n))
    )
  )
