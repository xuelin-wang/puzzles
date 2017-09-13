;;There is a strange printer with the following two special requirements:
(ns ^{:doc "
The printer can only print a sequence of the same character each time.
At each turn, the printer can print new characters starting from and ending at any places,
and will cover the original existing characters.
Given a string consists of lower English letters only,
your job is to count the minimum number of turns the printer needed in order to print it.

Example 1:
Input: "aaabbb"
Output: 2
Explanation: Print "aaa" first and then print "bbb".
Example 2:
Input: "aba"
Output: 2
Explanation: Print "aaa" first and then print "b" from the second place of the string,
which will cover the existing character 'a'.
Hint: Length of the given string will not exceed 100.

Analysis:
only need consider string with no consecutive same chars.
assume print always only print smallest length if possible.
r=ab..ax..
assume ith step if a:0..m (m can be 0)
no prints after ith cross m, so later ops either 1..m-1 or m+1..
move all ops >= m+1 to the end.
so we have this:
... a:0..m | ops:1..m-1 | ops: m+1..


f(a) = 1

f(ab) = 2

abc = 3
aba = 2

abc{a,b,d} = {3,3,4}
aba{b,c} = {3,3}

f(xy) =
  c is new char: f(r) + 1
  c is not new: can be done through a prev c (when?):m..n by extending m..k: f(r)
                can not: f(r) + 1

  consider last print of c:m..n,
  where there is a z


"
      :author "Xuelin Wang"}
  xl.lang.xl.strange-printer)

(defn print-times[s]
  (reduce-kv
    (fn [result i c]
      (case i
        0 1
        1 2

        (let [j (.lastIndexOf s c)]
          (if (neg? j)
            (inc result)
            (if (or (zero? j)
                    (let [cs (.substring s (inc j) i)
                          prefix (.substring s 0 j)]
                      (every? #(neg-int? (.indexOf prefix %)) (set (seq cs)))))
              result
              (inc result)
              )
            )
          )
        )
      )
    0
    (seq s)
    )
  )

(defn samples []
  (doseq [s ["a" "ab" "abb" "abc"]]
    (println "count for " s " is: " (print-times s))
    )
  )
