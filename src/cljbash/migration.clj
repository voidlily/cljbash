(ns cljbash.migration
  (:require [clojure.java.jdbc :as sql]))

(defn create-quotes []
  (sql/with-connection (str "sqlite://" (System/getProperty "user.dir") "/quotes.db")
    (sql/create-table :quotes
                       [:id :integer "PRIMARY KEY" "AUTOINCREMENT"]
                       [:text :text "NOT NULL"]
                       [:created_at :timestamp "NOT NULL" "DEFAULT CURRENT_TIMESTAMP"])))

(defn -main []
  (print "Creating database... ") (flush)
  (create-quotes)
  (println "Done"))