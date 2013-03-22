(ns cljbash.db
  (:use korma.core
        korma.db))

(defdb db (sqlite3 {:db "quotes.db"}))

(defentity quotes
  (entity-fields :text :created_at))

(defn insert-quote [text]
  (insert quotes
          (values {:text text})))

(defn latest-quotes [max-quotes]
  (select quotes
          (order :created_at :desc)
          (order :id :desc)
          (limit max-quotes)))

(defn random-quotes [max-quotes]
  (select quotes
          (order (raw "RANDOM()"))
          (limit max-quotes)))

(defn get-quote-by-id [id]
  (get (select quotes
               (where {:id id})) 0))
