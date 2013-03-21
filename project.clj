(defproject cljbash "0.1.0-SNAPSHOT"
  :description "An IRC quote database"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [compojure "1.1.5"]
                 [hiccup "1.0.2"]
                 [hiccup-bootstrap "0.1.1"]]
  :plugins [[lein-ring "0.8.2"]]
  :ring {:handler cljbash.handler/app}
  :profiles
  {:dev {:dependencies [[ring-mock "0.1.3"]]}})
