(ns xl.valid-number
  (:require [clojure.string])
  (:import (com.google.common.base Strings)))

(defn is-no-sign-int? [ss]
  (if (Strings/isNullOrEmpty ss)
    false
    (every? #(and (>= (int %) (int \0)) (<= (int %) (int \9))) (seq ss))
    )
  )

(defn is-no-sign-e-number? [ss]
  (if (Strings/isNullOrEmpty ss)
    false
    (let [d-i (.indexOf ss \.)]
      (cond
        (neg? d-i)
          (is-no-sign-int? ss)
        (zero? d-i)
          (is-no-sign-int? (.substring ss 1))
        (= d-i (dec (.length ss)))
          (is-no-sign-int? (.substring ss 0 (dec (.length ss))))
        :else
          (let [s1 (.substring ss 0 d-i)
                s2 (.substring ss (inc d-i))]
            (every? #(or (Strings/isNullOrEmpty %) (is-no-sign-int? %)) [s1 s2])
            )
        )
      )
    )
  )

(defn is-no-e-number? [ss]
  (cond
    (Strings/isNullOrEmpty ss) false
    (some #(.startsWith ss %) [\+ \-])
      (is-no-sign-e-number? (.substring ss 1))
    :else (is-no-sign-e-number? ss)
    )
  )

(defn is-number?
  "Check if a string is a valid number.
  1. assume decimal, both int and double.
  2. can have exponents.
  Try to be complete about edge cases:
  So possibly contains at most one e. if Not, a no-e-number.
  Otherwise, a simple number [e] a simple number.
  A simple number can optionally starts with + or -.
  Rest of a simple number with optional sign removed, no sign simple number:
  no sign simple number can contain 0 or 1 dot. Separate number to part 1 and part2.
  part 1 and 2 can not be both empty, but either can be empty.
  non empty parts must only contain 0-9, if starts with 0, can only be single 0, nothing else.
  "
  [ss]
  (if (Strings/isNullOrEmpty ss)
    false
    (let [e-i (.indexOf ss \e)]
      (cond
        (neg? e-i) (is-no-e-number? ss)
        (or (zero? e-i) (= e-i (dec (.length ss)))) false
        :else
        (let [s1 (.substring ss 0 e-i)
              s2 (.substring ss (inc e-i))]
          (every? is-no-e-number? [s1 s2])
          )
        )
      )
    )
  )

