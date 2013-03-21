(ns cljbash.views.random
  (:use cljbash.views.layout
        [cljbash.views.util :as util]))

(defn view-random [quotes]
  (layout "Random"
          (list
           [:h1 "Random quotes"]
           (map util/render-quote quotes))
          :random))
