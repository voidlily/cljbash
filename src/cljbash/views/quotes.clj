(ns cljbash.views.quotes
  (:use cljbash.views.layout)
  (:require [cljbash.views.util :as util]))

(defn view-quote [row]
  (layout "Quote"
          (util/render-quote row)
          :quote))
