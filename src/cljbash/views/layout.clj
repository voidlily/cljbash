(ns cljbash.views.layout
  (:use hiccup.core
        hiccup.page
        hiccup.bootstrap.page))

(def nav
  [
   {:key :home :name "Home" :href "/"}
   {:key :latest :name "Latest" :href "/latest"}
   {:key :random :name "Random" :href "/random"}
   {:key :browse :name "Browse" :href "/browse"}
   {:key :top :name "Top 100 Quotes" :href "/top"}
   {:key :add :name "Add Quote" :href "/add"}])

(defn nav-fixed [nav active]
  [:div.navbar.navbar-fixed
   [:div.navbar-inner
    [:div.container
     [:a.brand {:href "/"} "cljbash"]
     [:ul.nav
      (map #(vector :li
                    (when (= (% :key) active) {:class "active"})
                    [:a {:href (% :href)} (% :name)]) nav)]]]])

(defn layout [title body active-nav]
  (html5
   [:head
    [:title title]
    (include-bootstrap)]
   [:body
    (nav-fixed nav active-nav)
    [:div.container body]]))
