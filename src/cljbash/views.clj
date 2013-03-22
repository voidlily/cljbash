(ns cljbash.views
  (:use hiccup.core
        hiccup.page
        hiccup.util
        hiccup.bootstrap.page))

(def nav
  [{:key :home :name "Home" :href "/"}
   {:key :latest :name "Latest" :href "/latest"}
   {:key :random :name "Random" :href "/random"}
   {:key :random-good :name "Random > 0" :href "/random-good"}
   {:key :browse :name "Browse" :href "/browse"}
   {:key :top :name "Top Quotes" :href "/top"}
   {:key :add :name "Add Quote" :href "/add"}])

(defn nav-fixed [nav active]
  [:div.navbar.navbar-fixed
   [:div.navbar-inner
    [:div.container
     [:a.brand {:href "/"} "cljbash"]
     [:ul.nav
      (map #(vector :li
                    (when (= (% :key) active) {:class "active"})
                    [:a {:href (% :href)} (% :name)]) nav)]
     [:ul.nav.pull-right
      [:li [:form.navbar-search.pull-right
            [:input.search-query {:type "text" :placeholder "Search"}]]]]]]])

(defn layout [title body active-nav]
  (html5
   [:head
    [:title (str "QDB: " title)]
    (include-bootstrap)]
   [:body
    (nav-fixed nav active-nav)
    [:div.container body]]))

(defn pagination [url-base current-page num-pages]
  [:div.pagination
   [:ul
    (map
     #(let [li-class (if (= % current-page) :li.active :li)]
        [li-class
         [:a {:href (str url-base "?page=" %)} %]])
     (range 1 (+ num-pages 1)))]])

(defn quote-link [id]
  (str "/quotes/" id))

(defn vote-link [id direction]
  (str "/quotes/" id "/vote?direction=" (name direction)))

(defn render-quote [row]
  (let [id (row :id)
        score (row :total_score)]
    (list
     [:div.quote-info
      [:span.quote-number
       [:a {:href (quote-link id)} (str "#" id)]]
      " "
      [:span.quote-score
       [:a {:href (vote-link id :up)} (str "[+]")]
       (cond
        (< score 0) [:span.badge.badge-important score]
        (= score 0) [:span.badge score]
        (> score 0) [:span.badge.badge-success score])
       [:a {:href (vote-link id :down)} (str "[-]")]]]
     [:div.quote  [:pre (escape-html (row :text))]])))

(defn view-add
  ([] (view-add {}))
  ([{:keys [status] :or {status nil}}]
     (layout "Add Quote"
             (list
              [:h1 "Add Quote"]
              [:div.container
               (cond
                (= status :success) [:div.alert.alert-success "Quote submitted"]
                (= status :error) [:div.alert.alert-error "There was an error submitting your quote."])
               [:form {:action "/add" :method "POST"}
                [:textarea {:class (str "span" 12) :rows 10 :name "text"}]
                [:button.btn {:type "submit"} "Submit"]]])
             :add)))

(defn wrap-pagination [url page num-pages & content]
  (list (pagination url page num-pages)
        content
        (pagination url page num-pages)))

(defn view-browse [quotes page num-pages]
  (layout "Browse"
          (list
           [:h1 "Browse quotes"]
           (wrap-pagination "/browse" page num-pages
                            (map render-quote quotes)))
          :browse))

(defn view-index []
  (layout "Hello World!"
          (list
           [:h1 "My name is Homestar Runner!"]
           [:div {:id "content"} "And this is a website!"])
          :home))

(defn view-latest [quotes page num-pages]
  (layout "Latest"
          (list
           [:h1 "Latest quotes"]
           (wrap-pagination "/latest" page num-pages
                            (map render-quote quotes)))
          :latest))

(defn view-quote [row]
  (layout (str "Quote #" (row :id))
          (render-quote row)
          :quote))

(defn view-quote-not-found [id]
  (layout (str "Quote #" id)
          [:div (str "Quote #" id " not found")]
          :quote))

(defn view-random [quotes]
  (layout "Random"
          (list
           [:h1 "Random quotes"]
           (map render-quote quotes))
          :random))

(defn view-random [quotes]
  (layout "Random > 0"
          (list
           [:h1 "Random quotes > 0"]
           (map render-quote quotes))
          :random-good))

(defn view-top [quotes page num-pages]
  (layout "Top"
          (list
           [:h1 "Top quotes"]
           (wrap-pagination "/top" page num-pages
                            (map render-quote quotes)))
          :top))

(defn view-vote [id direction]
  (layout "Voting"
          (list
           [:div (str "Quote " id " successfully voted " direction)])
          :vote))