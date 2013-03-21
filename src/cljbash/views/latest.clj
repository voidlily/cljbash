(ns cljbash.views.latest
  (:use cljbash.views.layout
        [cljbash.views.util :as util]))

(defn view-latest [quotes]
  (layout "Latest"
          (list
           [:h1 "Latest quotes"]
           (map util/render-quote quotes))
          :latest))
