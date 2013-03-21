(ns cljbash.views.index
  (:use hiccup.core
        hiccup.page
        cljbash.views.layout))

(defn view-index []
  (layout "Hello World!"
          (list
           [:h1 "A message from our president"]
           [:div {:id "content"} "Hello World!"])
          :home))
