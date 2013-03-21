(defproject cljbash "0.1.0-SNAPSHOT"
  :description "An IRC quote database"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [compojure "1.1.5"]
                 [hiccup "1.0.2"]
                 [hiccup-bootstrap "0.1.1"]
                 [korma "0.3.0-RC4"]
                 [org.clojure/java.jdbc "0.2.3"]
                 [org.xerial/sqlite-jdbc "3.7.2"]]
  :plugins [[lein-ring "0.8.2"]]
  :ring {:handler cljbash.handler/app}
  :profiles
  {:dev {:dependencies [[ring-mock "0.1.3"]]}})
