(ns cljbash.views.browse
  (:use cljbash.views.layout
        [cljbash.views.util :as util]))

(defn view-browse [quotes]
  (layout "Browse"
          (list
           [:h1 "Browse quotes"]
           (map util/render-quote quotes))
          :browse))
