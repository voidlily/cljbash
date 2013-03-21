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
  (map #(% :text) (select quotes
                         (fields :text)
                         (order :created_at :desc)
                         (order :id :desc)
                         (limit max-quotes))))

(defn random-quotes [max-quotes]
  (map #(% :text) (select quotes
                         (fields :text)
                         (order (raw "RANDOM()"))
                         (limit max-quotes))))
