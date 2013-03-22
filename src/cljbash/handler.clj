(ns cljbash.handler
  (:use compojure.core
        hiccup.bootstrap.middleware)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [cljbash.db :as db]
            [cljbash.views :as views]
            [ring.util.response :as response]))

(def latest-quotes
  (clojure.string/split (slurp "/Users/voidlily/dev/fortune/quotes") #"\n%\n"))

(defn handle-add [text]
  (println text)
  (views/view-add {:status :success}))

(defn handle-get-by-id [id]
  (let [row (db/get-quote-by-id id)]
    (if row
      (views/view-quote row)
      (views/view-quote-not-found id))))

(defroutes app-routes
  (GET "/" [] (views/view-index))
  (GET "/latest" [] (views/view-latest (db/latest-quotes 50)))
  (GET "/random" [] (views/view-random (db/random-quotes 5)))
  (GET "/browse" [] (views/view-browse (map #(do {:id -1 :text %}) latest-quotes)))
  (GET "/top" [] (views/view-top (map #(do {:id -1 :text %}) latest-quotes)))
  (GET "/add" [] (views/view-add))
  (POST "/add" [text] (handle-add text))
  (POST "/search" [] "Search")
  (GET "/quotes/:id" [id] (handle-get-by-id id))
  (route/not-found "Not Found"))

(def app
  (wrap-bootstrap-resources (handler/site app-routes)))
