(ns cljbash.db
  (:use korma.core
        korma.db))

(defdb db (sqlite3 {:db "quotes.db"}))

(defentity quotes
  (entity-fields :text :created_at))

(defn insert-quote [text]
  (insert quotes
          (values {:text text})))