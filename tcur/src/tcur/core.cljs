(ns tcur.core
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [reagent.core :as r :refer [atom]]))

(enable-console-print!)

(defonce state
         (atom {:text "Hello Curly!"}))

(defn main []
  [:div (:text @state)])

(r/render-component [main]
                    (.getElementById js/document "app"))
