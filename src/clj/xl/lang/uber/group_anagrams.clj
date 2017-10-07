(ns xl.lang.uber.group-anagrams)

(defn solve [words]
  (let [ss #(apply str (sort (seq %)))]
    (group-by ss words)
    )
  )

(defn samples []
  (let [res          (solve ["eat", "tea", "tan", "ate", "nat", "bat"])
        ]
    (println res)
    )
         )

(samples)