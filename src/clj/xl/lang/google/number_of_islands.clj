"
A 2d grid map of m rows and n columns is initially filled with water. We may perform an addLand operation which turns the water at position (row, col) into a land. Given a list of positions to operate, count the number of islands after each addLand operation. An island is surrounded by water and is formed by connecting adjacent lands horizontally or vertically. You may assume all four edges of the grid are all surrounded by water.

Example:

Given m = 3, n = 3, positions = [[0,0], [0,1], [1,2], [2,1]].
Initially, the 2d grid grid is filled with water. (Assume 0 represents water and 1 represents land).

0 0 0
0 0 0
0 0 0
Operation #1: addLand(0, 0) turns the water at grid[0][0] into a land.

1 0 0
0 0 0   Number of islands = 1
0 0 0
Operation #2: addLand(0, 1) turns the water at grid[0][1] into a land.

1 1 0
0 0 0   Number of islands = 1
0 0 0
Operation #3: addLand(1, 2) turns the water at grid[1][2] into a land.

1 1 0
0 0 1   Number of islands = 2
0 0 0
Operation #4: addLand(2, 1) turns the water at grid[2][1] into a land.

1 1 0
0 0 1   Number of islands = 3
0 1 0
We return the result as an array: [1, 1, 2, 3]

Challenge:

Can you do it in time complexity O(k log mn), where k is the length of the positions?

Analysis:
to turn it into a union-join problem: initially, set root[i, j] = [i, j] or nil.
whenever a new island is added, check all its neighbors and union when see one.
keep track of current possible islands cells, one per island at most.
the number of islands is the number of potential roots which has root[i,j] is itself.
So cost is:
add: depth of node to root, no more than number of adds, we collapse path heuristically.

"

(ns xl.lang.google.number-of-islands)

(defn islands [row col add-cells]
  (let [deltas [[0 1] [1 0] [0 -1] [-1 0]]
        get-neighbors
          (fn [[r c]]
            (for [[delta-r delta-c] deltas
                  :let [nr (+ r delta-r) nc (+ c delta-c)]
                  :when (and (nat-int? nr) (< nr row) (nat-int? nc) (< nc col))
                  ]
                                            [nr nc])
            )
        merge-roots
          (fn [cell1 cell2 [roots root-cells]]
            (let [find-root-path
                  (fn [cell]
                    (loop [path [cell]]
                      (let [root (roots cell)]
                        (if (= root cell) path
                                          (recur (conj path root)))))
                    )
                  ]
              (if (every? identity (map (comp some? roots) [cell1 cell2]))
                (let [[path1 path2] (map find-root-path [cell1 cell2])
                      [depth1 depth2] (map count [path1 path2])
                       new-root (if (<= depth1 depth2) (last path2) (last path1))
                      new-roots
                      (reduce
                        #(assoc %1 %2 new-root)
                        roots
                        (concat path1 path2))
                      new-root-cells (disj root-cells (last path1) (last path2))
                      new-root-cells (conj new-root-cells new-root)
                      ]
                  [new-roots new-root-cells]
                  )
                [roots root-cells]
                )

              )

              )
        ]
    (reduce
      (fn [{:keys [counts roots root-cells]} add-cell]
          (let [roots (assoc roots add-cell add-cell)
                root-cells (conj root-cells add-cell)
                [roots root-cells] (reduce
                        #(merge-roots add-cell %2 %1)
                        [roots root-cells]
                        (get-neighbors add-cell))
                new-count (reduce
                             (fn [c root-cell]
                               (if (= root-cell (roots root-cell)) (inc c) c)
                               )
                             0 root-cells)
                ]
            {:counts (conj counts new-count) :roots roots :root-cells root-cells}
            )
        )
      {:counts [] :roots {} :root-cells #{}}
      add-cells)

    )

  )


(defn samples []
  (let [row 3 col 3 add-cells [[0,0], [0,1], [1,2], [2,1]]]
    (println (:counts (islands row col add-cells)))
    )
  )

(samples)
