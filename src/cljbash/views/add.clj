(ns cljbash.views.add
  (:use cljbash.views.layout))

(defn view-add [{:keys [status] :or {status nil}}]
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
          :add))
