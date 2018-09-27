(defproject puzzle "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [org.clojure/spec.alpha "0.2.168"]
                 [org.clojure/test.check "0.10.0-alpha3"]
                 [org.clojure/math.combinatorics "0.1.4"]
                 [org.clojure/core.async "0.4.474"]
                 [org.clojure/math.combinatorics "0.1.4"]
                 [org.clojure/math.numeric-tower "0.0.4"]
                 [com.datomic/client-cloud "0.8.63"]
                 [org.clojure/algo.monads "0.1.6"]]

  :java-source-paths ["src/java"]
  :javac-options ["-target" "1.8" "-source" "1.8"]
  :source-paths ["src/clj" "src/cljc"]
  :resource-paths ["resources"]
  :target-path "target/%s/"


  :profiles
  {:uberjar {:omit-source true
             :prep-tasks [["javac"] ["compile"]]

             :aot :all
             :uberjar-name "testlein.jar"
             :source-paths ["env/prod/clj"]
             :resource-paths ["env/prod/resources"]}


   :dev           [:project/dev :profiles/dev]
   :test          [:project/dev :project/test :profiles/test]

   :project/dev  {:dependencies [[prone "1.6.0"]
                                 [pjstadig/humane-test-output "0.8.3"]
                                 [binaryage/devtools "0.9.10"]
                                 [com.cemerick/piggieback "0.2.2"]
                                 [com.google.guava/guava "26.0-jre"]
                                 [doo "0.1.10"]]
                  :plugins      [[com.jakemccrary/lein-test-refresh "0.23.0"]
                                 [lein-doo "0.1.10"]]

                  :doo {:build "test"}
                  :source-paths ["env/dev/clj" "test/clj"]
                  :resource-paths ["env/dev/resources"]
                  :repl-options {:init-ns user}
                  :injections [(require 'pjstadig.humane-test-output)
                               (pjstadig.humane-test-output/activate!)]}
   :project/test {:resource-paths ["env/test/resources"]}

   :profiles/dev {}
   :profiles/test {}
   :dontcare nil}
  )
