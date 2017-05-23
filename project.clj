(defproject testlein "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0-alpha16"]
                 [org.clojure/core.specs.alpha "0.1.10"]
                 [org.clojure/test.check "0.9.0"]
                 [org.clojure/math.combinatorics "0.1.4"]
                 [org.clojure/core.async "0.3.442"]]

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

   :project/dev  {:dependencies [[prone "1.1.4"]
                                 [pjstadig/humane-test-output "0.8.1"]
                                 [binaryage/devtools "0.8.3"]
                                 [com.cemerick/piggieback "0.2.2-SNAPSHOT"]
                                 [doo "0.1.7"]]
                  :plugins      [[com.jakemccrary/lein-test-refresh "0.18.1"]
                                 [lein-doo "0.1.7"]]

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
