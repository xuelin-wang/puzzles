(ns xl.reverse-pairs
  "Given an array nums, we call (i, j) an important reverse pair if i < j and nums[i] > 2*nums[j].

  You need to return the number of important reverse pairs in the given array.

  Example1:

  Input: [1,3,2,3,1]
  Output: 2
  Example2:

  Input: [2,4,3,5,1]
  Output: 3
  Note:
  The length of the given array will not exceed 50,000.
  All the numbers in the input array are in the range of 32-bit integer.

  Solution:
  1. Naive:
  n^2 time and constant space. comparing each pair.

  2. suppose we have a binary search tree. (L < M < R). Each node also maintains number of nodes with this value.
  For next value v, we traverse nodes with values < 2v to figure out number of important pairs with this j.
  Time is log j (average. not necessarily balanced tree). So total time is n log n. Space is O(n)
  "
  )

(defn add-to-bst [vi [[vk ck1 ck2] [vl cl1 cl2 :as left] [vr cr1 cr2 :as right]]]
  (cond
    (nil? vk)                                                ;nil node
      [[vi 1 0] nil nil]
    (= vi vk)
      [[vk (inc ck1) ck2] left right]
    (< vi vk)
      [[vk ck1 ck2] (add-to-bst vi left) right]
    :else
      [[vk ck1 (inc ck2)] left (add-to-bst vi right)]
    )
  )

(defn search-gt [v n [[vk ck1 ck2] left right]]
  (cond
    (nil? vk)                                                ;nil node
      n
    (= v vk)
      (+ ck2 n)
    (< v vk)
      (search-gt v (+ n ck1 ck2) left)
    :else
      (search-gt v n right)
    )
  )

(defn- find-important-pairs1 [nums]
  (reduce
    (fn [{:keys [bst pairs]} i]
      (let [ni (nth nums i)
            m (search-gt (* 2 ni) 0 bst)
            new-bst (add-to-bst ni bst)]
        {:bst new-bst :pairs (+ pairs m)}
        )
      )
    {:bst [] :pairs 0}
    (range (count nums))
    )
  )

(defn find-important-pairs [nums]
  (:pairs (find-important-pairs1 nums))
  )


(defn do-samples []
  (doseq [nums [
                [1 3 2 3 1]
                [2 4 3 5 1]
                ]]
    (println (str "nums: " nums ", paris: " (find-important-pairs nums)))
    )
  )

;(do-samples)