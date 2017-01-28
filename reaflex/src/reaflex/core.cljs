(ns reaflex.core
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [reagent.core :as r :refer [atom]]))

(enable-console-print!)

(defonce state (atom {:text "Hello Curly!"}))

(defn main* []
  [:div.wrap
   [:div.row.big
    [:div.box.medium.h2]
    [:div.column.medium
     [:div.row.big
      [:div.box.medium]
      [:div.box.medium]]
     [:div.row.big
      [:div.box.medium]
      [:div.box.medium]]]]
   [:div.box.medium]
   [:div.box.medium]
   [:div.box.big]])

(defn main []
  [:div
   {:display :flex
    :flex-flow "row wrap"}
   [:div
    {:style {:height :400px
             :flex "0 0 100%"
             :display :flex
             :flex-flow "row nowrap"}}
    [:div
     {:style
      {:height :400px
       :flex "0 0 50%"
       :border "5px solid white"
       :background :tomato}}]
    [:div
     {:style
      {:height :400px
       :flex "0 0 50%"
       :display :flex
       :flex-flow "column nowrap"}}
     [:div {:style
            {:flex "1 1 50%"
             :display :flex
             :flex-flow "row nowrap"}}
      [:div {:style
             {:flex "1 1 50%"
              :border "5px solid white"
              :background :tomato}}]
      [:div {:style
             {:flex "1 1 50%"
              :border "5px solid white"
              :background :tomato}}]]
     [:div {:style
            {:flex "1 1 50%"
             :display :flex
             :flex-flow "row nowrap"}}
      [:div {:style
             {:flex "1 1 50%"
              :border "5px solid white"
              :background :tomato}}]
      [:div {:style
             {:flex "1 1 50%"
              :border "5px solid white"
              :background :tomato}}]]]]])

(r/render-component [main]
                    (.getElementById js/document "app"))


