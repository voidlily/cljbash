(ns cljbash.handler
  (:use compojure.core
        hiccup.bootstrap.middleware)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [cljbash.db :as db]
            (cljbash.views
             [index :as index]
             [latest :as latest]
             [random :as random]
             [browse :as browse]
             [top :as top]
             [add :as add])))

(def latest-quotes
  ["<hq1> i promised myself not to touch java EVER in my life again, i'd rather drive a taxi"
   "Rachel: I'm board.
Jeff: I'm chalk, we should get together.
Rachel: BOARD! Like I don't have anything to do, not BORD, like a chalkbord. Learn to spellcheck.
Jeff: Oh god I hope you don't breed."
   "<Me> the nigerian prince scam is old
<Me> today you do it like this:
<Me> hi, im an arabic dictator, and i have to get my vast fortune outside the country soon"]
  )

(defn handle-add [text]
  (println text)
  (add/view-add {:status :success}))

(defroutes app-routes
  (GET "/" [] (index/view-index))
  (GET "/latest" [] (latest/view-latest (db/latest-quotes 50)))
  (GET "/random" [] (random/view-random (db/random-quotes 5)))
  (GET "/browse" [] (browse/view-browse latest-quotes))
  (GET "/top" [] (top/view-top latest-quotes))
  (GET "/add" [] (add/view-add))
  (POST "/add" [text] (handle-add text))
  (POST "/search" [] "Search")
  (GET "/quotes/:id" [id] (quotes/view-quote (db/get-quote-by-id id)))
  (route/not-found "Not Found"))

(def app
  (wrap-bootstrap-resources (handler/site app-routes)))
