(ns cljbash.views.top
  (:use cljbash.views.layout
        [cljbash.views.util :as util]))

(defn view-top [quotes]
  (layout "Top"
          (list
           [:h1 "Top quotes"]
           (map util/render-quote quotes))
          :top))
