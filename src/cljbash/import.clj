(ns cljbash.import
  (:require [cljbash.db :as db]))

(defn -main [filename]
  (let [quotes (clojure.string/split (slurp filename) #"\n%\n")]
    (doseq [quote quotes]
      (db/insert-quote quote))))