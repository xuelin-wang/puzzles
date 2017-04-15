(ns xl.polymorphism)

(defn f
  "poly by param arity"
  ([] (println "noarg"))
  ([s] (println "one arg: " s))
  )

;(f)
;(f "first")

(defmulti mf "multimethod" )

