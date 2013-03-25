(ns cljbash.handler
  (:use compojure.core
        hiccup.bootstrap.middleware)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [cljbash.db :as db]
            [cljbash.views :as views]))

(defn handle-add [text]
  (db/insert-quote text)
  (views/view-add {:status :success}))

(defn handle-latest [page]
  (let [page (if page (Integer/parseInt page) 1)
        [quotes num-pages] (db/latest-quotes 50 page)]
    (views/view-latest quotes page num-pages)))

(defn handle-browse [page]
  (let [page (if page (Integer/parseInt page) 1)
        [quotes num-pages] (db/browse-quotes 50 page)]
    (views/view-browse quotes page num-pages)))

(defn handle-top [page]
  (let [page (if page (Integer/parseInt page) 1)
        [quotes num-pages] (db/top-quotes 100 page)]
    (views/view-top quotes page num-pages)))

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
  (GET ["/latest" :page #"[0-9]*"] [page] (handle-latest page))
  (GET "/random" [page] (views/view-random (db/random-quotes 5)))
  (GET "/random-good" [page] (views/view-random-good (db/random-good-quotes 5)))
  (GET ["/browse" :page #"[0-9]*"] [page] (handle-browse page))
  (GET ["/top" :page #"[0-9]*"] [page] (handle-top page))
  (GET "/add" [] (views/view-add))
  (POST "/add" [text] (handle-add text))
  (POST "/search" [] "Search")
  (GET ["/quotes/:id", :id #"[0-9]+"] [id] (handle-get-by-id id))
  (GET ["/quotes/:id/vote", :id #"[0-9]+", :direction #"(up|down)"] [id direction] (handle-vote id direction))
  (route/not-found "Not Found"))

(def app
  (wrap-bootstrap-resources (handler/site app-routes)))
