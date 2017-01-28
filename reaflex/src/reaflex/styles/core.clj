(ns reaflex.styles.core
  (:require [garden.def :refer [defstylesheet defstyles]]
    [garden.units :refer [px]]))

(def flex-row {:display :flex :flex-flow "row wrap" :justify-content :center})
(def flex-column {:display :flex :flex-flow "column wrap" :justify-content :center})

(def base-height 100)

(def timepicker
  [:.timepicker
   {:background :tomato
    :display :inline-block
    :border-radius "5px"}
   [:input {:background "rgba(255,255,255,.3)"
            :width :26px
            :text-align :center
            :border :none
            :font-size :16px
            :border-radius "3px"
            :margin "5px 2px"}
    [:&:first-child {:margin-left "5px"}]
    [:&:last-child {:margin-right "5px"}]
    [:&:focus {:background "rgba(255,255,255,.6)"
               :outline :none}]]])

;; Change defstylesheet to defstyles.
(defstyles screen
           [:html {:box-sizing :border-box}
            ["*, *:before, *:after" {:box-sizing :inherit}]
            timepicker
            [:.wrap
             flex-row
             [:.box {:height (str base-height "px") :flex-grow 1 :background :lightgrey :margin :5px}]
             [:.row flex-row {:flex "1 1"}]
             [:.column flex-column]
             [:.big {:flex-basis :800px}]
             [:.medium {:flex-basis :400px}]
             [:.little {:flex-basis :200px}]
             (for [x (range 10)]
               [(str ".h" x) {:height (str (* x base-height) "px")}])]])
