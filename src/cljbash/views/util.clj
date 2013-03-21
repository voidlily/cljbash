(ns cljbash.views.util
  (:use hiccup.util))

(defn render-quote [quote]
  [:div {:class "quote"} [:pre (escape-html quote)]])
