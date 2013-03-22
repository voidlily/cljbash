(ns cljbash.db
  (:use korma.core
        korma.db))

(defdb db (sqlite3 {:db "quotes.db"}))

(defentity quotes
  (entity-fields :text :created_at))

(defentity votes
  (entity-fields :quote_id :score))

(defentity quotes-with-votes
  (table (subselect quotes
                    (fields :id :text :created_at (raw "COALESCE(SUM(votes.score), 0) AS total_score"))
                    (join votes (raw "votes.quote_id = quotes.id"))
                    (group :quotes.id))
         :quotes-with-votes))

(defn insert-quote [text]
  (insert quotes
          (values {:text text})))

(defn latest-quotes [max-quotes page-number]
  "Gets `max-quotes` latest quotes."
  (let [row-offset (* max-quotes (- page-number 1))]
    [
     (select quotes-with-votes
             (order :created_at :desc)
             (order :id :desc)
             (limit max-quotes)
             (offset row-offset))
     (let [count-result
           (select quotes
                   (fields "COUNT(*) AS count"))
           count ((get count-result 0) :count)]
       (Math/ceil (/ count max-quotes)))]))

(defn random-quotes [max-quotes]
  "Gets `max-quotes` number of random quotes."
  (select quotes-with-votes
          (order (raw "RANDOM()"))
          (limit max-quotes)))

(defn random-good-quotes [max-quotes]
  "Gets `max-quotes` number of random quotes."
  (select quotes-with-votes
          (order (raw "RANDOM()"))
          (where (> :total_score 0))
          (limit max-quotes)))

(defn top-quotes [max-quotes page-number]
  "Gets the top scoring `max-quotes` number of quotes."
  (let [row-offset (* max-quotes (- page-number 1))]
    [
     (select quotes-with-votes
             (order :total_score :desc)
             (order :created_at :desc)
             (order :id :desc)
             (limit max-quotes)
             (offset row-offset))
     (let [count-result
           (select quotes-with-votes
                   (fields "COUNT(*) as count")
                   (order :total_score :desc)
                   (order :created_at :desc)
                   (order :id :desc))
           count ((get count-result 0) :count)]
       (Math/ceil (/ count max-quotes)))]))

(defn browse-quotes [max-quotes page-number]
  (let [row-offset (* max-quotes (- page-number 1))]
    [
     (select quotes-with-votes
             (limit max-quotes)
             (offset row-offset))
     (let [count-result
           (select quotes
                   (fields "COUNT(*) AS count"))
           count ((get count-result 0) :count)]
       (Math/ceil (/ count max-quotes)))]))

(defn get-quote-by-id [id]
  "Get a single quote by ID."
  (get (select quotes-with-votes
               (where {:id id})) 0))

(defn vote-direction-to-score [direction]
  (cond
   (= direction :up) 1
   (= direction :down) -1))

(defn add-vote [id direction]
  (when-let [score (vote-direction-to-score direction)]
    (insert votes
            (values {:quote_id id :score score}))))
