(ns cljbash.handler
  (:use compojure.core
        hiccup.bootstrap.middleware)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [cljbash.db :as db]
            [cljbash.views :as views]))

(def latest-quotes
  (clojure.string/split (slurp "/Users/voidlily/dev/fortune/quotes") #"\n%\n"))

(defn handle-add [text]
  (db/insert-quote text)
  (views/view-add {:status :success}))

(defn handle-get-by-id [id]
  (let [row (db/get-quote-by-id id)]
    (if row
      (views/view-quote row)
      (views/view-quote-not-found id))))

(defn handle-vote [id direction]
  (db/add-vote id (keyword direction))
  (views/view-vote id direction))

(defroutes app-routes
  (GET "/" [] (views/view-index))
  (GET ["/latest" :page #"[0-9]*"] [page]
       (views/view-latest (db/latest-quotes 50 (if page (Integer/parseInt page) 1))))
  (GET "/random" [page] (views/view-random (db/random-quotes 5)))
  (GET ["/browse" :page #"[0-9]*"] [page]
       (views/view-browse (db/browse-quotes 50 (if page (Integer/parseInt page) 1))))
  (GET ["/top" :page #"[0-9]*"] [page]
       (views/view-top (db/top-quotes 100 (if page (Integer/parseInt page) 1))))
  (GET "/add" [] (views/view-add))
  (POST "/add" [text] (handle-add text))
  (POST "/search" [] "Search")
  (GET ["/quotes/:id", :id #"[0-9]+"] [id] (handle-get-by-id id))
  (GET ["/quotes/:id/vote", :id #"[0-9]+", :direction #"(up|down)"] [id direction] (handle-vote id direction))
  (route/not-found "Not Found"))

(def app
  (wrap-bootstrap-resources (handler/site app-routes)))
