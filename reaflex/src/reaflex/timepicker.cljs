(ns reaflex.timepicker
  (:require-macros [reagent.ratom :refer [reaction run!]])
  (:require [reagent.core :as r :refer [atom]]
            [cljs-time.core :as t]
            [cljs-time.format :as format]))

(comment "run! trying"
         (let [state (atom {:a {:b 1}})
               c (r/cursor state [:a])
               r (reaction (inc (:b @c)))
               x (atom nil)]
           (run! (do (println "run!")
                     (reset! x (inc @r))))
           (println @x)
           (swap! c update :b dec)
           (println @x)))

(defn hm->date [{:keys [year month day hours minutes seconds] :as hm}]
  (t/date-time year month day hours minutes seconds))

(def formatter (format/formatter "yyyy-MM-ddTHH:mm:ssZ"))

(defn datetime->str [date]
  (if (nil? date)
    ""
    (->> date
         t/to-default-time-zone
         (format/unparse formatter))))

(defn str->datetime [s]
  (when (seq s)
    (->> s
         (format/parse formatter)
         t/to-default-time-zone)))

(defn date->hm [d]
  {:year (.getYear d)
   :month (inc (.getMonth d))
   :day (.getDate d)
   :hours (.getHours d)
   :minutes (.getMinutes d)
   :seconds (.getSeconds d)})

(defn tp-field-val [max val]
  (rem (+ max val) max))

(defn tp-field-str [max val]
  (let [val (tp-field-val max val)
        one-digit? (> 10 val)]
    (if one-digit?
      (str "0" val)
      (str val))))

(defn timepicker-field [max cursor & [class]]
  (let [timeout (cljs.core/atom nil)
        interval (cljs.core/atom nil)
        go! (fn [f]
              (reset! timeout
                      (js/setTimeout
                        (fn []
                          (reset! interval (js/setInterval #(swap! cursor f) 75)))
                        400)))
        stop! (fn []
                (js/clearTimeout @timeout)
                (reset! timeout nil)
                (js/clearInterval @interval)
                (reset! interval nil))]
    (r/create-class
      {:component-will-unmount stop!
       :reagent-render
       (fn []
         [:input {:style {:width :26px
                          :text-align :center
                          :border :none
                          :font-size :16px}
                  :class class
                  :on-change (fn [e]
                               (reset! cursor (tp-field-val max (int (.. e -target -value)))))
                  :on-key-down (fn [e]
                                 (when (and (not @interval) (not @timeout))
                                   (condp = (.-which e)
                                     38 (do (swap! cursor inc) (go! inc))
                                     40 (do (swap! cursor dec) (go! dec))
                                     nil)))
                  :on-key-up (fn [] (stop!))
                  :value (tp-field-str max @cursor)}])})))

(defn timepicker [{:keys [date-ref]}]
  (let [date-hm (atom (date->hm @date-ref))
        hours (r/cursor date-hm [:hours])
        minutes (r/cursor date-hm [:minutes])
        seconds (r/cursor date-hm [:seconds])]
    (run! (reset! date-ref (hm->date @date-hm)))
    (run! (println @date-ref))
    (fn []
      [:div.timepicker
       [timepicker-field 24 hours]
       ":"
       [timepicker-field 60 minutes]
       ":"
       [timepicker-field 60 seconds]])))

(r/render [timepicker {:date-ref (atom (t/date-time 2016 0 0 12 13 15))}]
          (.getElementById js/document "app"))



(defn reacur [ref f1 f2]
  (assert (= @ref (f2 (f1 @ref))))
  (let [res (atom (f1 @ref))]
    (run! (reset! res (f1 @ref)))
    (run! (reset! ref (f2 @res)))
    res))

(let [state (atom 0)
      view (reacur state inc dec)]
  (println @state @view)
  (swap! view inc)
  (println @state @view)
  (swap! state inc)
  (println @state @view))

(defn resizable [cb]
  {:component-did-mount (fn [this] (.resize (js/$ js/window) #(cb this)))})

(defn composite-class [& specs]
  (r/create-class (apply merge-with juxt specs)))

(defn comp1 [props & childs]
  (create-class
    {:reagent-render (fn [] [:div childs])
     :component-did-mount (fn [this] (println (r/children this)
                                              (r/props this)))}
    (resizable (fn [this] (println (r/dom-node this))))))

(r/render [comp1 {} [:div "hello"]]
          (.getElementById js/document "app"))

