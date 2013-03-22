(ns cljbash.migration
  (:require [clojure.java.jdbc :as sql]))

(defn create-quotes [db]
  (sql/create-table :quotes
                    [:id :integer "PRIMARY KEY" "AUTOINCREMENT"]
                    [:text :text "NOT NULL"]
                    [:created_at :timestamp "NOT NULL" "DEFAULT CURRENT_TIMESTAMP"]))

(defn create-votes []
  (sql/create-table :votes
                    [:id :integer "PRIMARY KEY" "AUTOINCREMENT"]
                    [:quote_id :integer "NOT NULL"]
                    [:score :integer "NOT NULL"]
                    [:created_at :timestamp "NOT NULL" "DEFAULT CURRENT_TIMESTAMP"]))

(defn create-votes-index []
  (sql/do-commands "CREATE INDEX votes_quote_id_idx ON votes (quote_id)"))

(defn -main []
  (print "Creating database... ") (flush)
  (let [db (str "sqlite://" (System/getProperty "user.dir") "/quotes.db")]
    (sql/with-connection db
      (create-quotes)
      (create-votes)))
  (println "Done"))