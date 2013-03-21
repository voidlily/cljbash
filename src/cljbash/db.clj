(ns cljbash.db
  (:use korma.core
        korma.db))

(defdb db (sqlite3 {:db "quotes.db"}))