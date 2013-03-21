(ns cljbash.handler
  (:use compojure.core
        hiccup.bootstrap.middleware)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            (cljbash.views
             [index :as index])))

(defroutes app-routes
  (GET "/" [] (index/view-index))
  (GET "/latest" [] "Latest")
  (GET "/random" [] "Random")
  (GET "/browse" [] "Browse")
  (GET "/top" [] "Top quotes")
  (GET "/add" [] "Add quote page")
  (POST "/add" [] "Add quote submission")
  (POST "/search" [] "Search")
  (route/not-found "Not Found"))

(def app
  (wrap-bootstrap-resources (handler/site app-routes)))
