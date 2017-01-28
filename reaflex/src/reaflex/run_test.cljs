(ns reaflex.run-test
  (:require [reagent.core :as r])
  (:require-macros [reagent.ratom :refer [reaction run!]]))

(enable-console-print!)

(def model (r/atom 1))

(println "A")

(run!
  (println "  >>>" @model)

  (when (> @model 1)
    (throw (ex-info "Oops" {}))))

; setTimeout (hopefully) simulates user clicking a button in UI after exception
(.setTimeout js/window
             #(do (println "B")
                  (swap! model inc)
                  (println "   new value:" @model))
             100)

(.setTimeout js/window
             #(do (println "C")
                  (swap! model inc)
                  (println "  new value:" @model))
             200)

(some? (list nil))

(reduce-kv assoc {} {:a 1 :b 2})
