(ns cljbash.views.util
  (:use hiccup.util))

(defn render-quote [row]
  (list
   [:div {:class "quote-info"} [:a {:href (str "/quotes/" (row :id))} (str "#" (row :id))]]
   [:div {:class "quote"} [:pre (escape-html (row :text))]]))
