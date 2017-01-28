(ns reaflex.druid
  (:require [cljs-http :as http]))

(defn query
  "Issue a druid query"
  [url {:keys [queryType] :as query}]
  (http/post url
             {:body (json/write-str (v/validate query queryType))
              :headers {"content-Type" "application/json"}}))

(defn )

(comment
  (connect {:hosts ["http://m1.vigiglo.be:8085/druid/v2/?pretty"]})

  (def q1
    {:queryType :timeseries
     :dataSource "dev.supercell"
     :granularity :day
     :filter {
              :type :and
              :fields [{:type :selector :dimension "project_id" :value "vigiglobe-Earthquake"}
                       {:type :selector :dimension "source" :value "twitter"}
                       {:type :selector :dimension "sex" :value "male"}]}
     :aggregations [#_{:type :cardinality :name "foo" :fieldNames ["authors"] :byRow false}
                    {:type :count :name "volume"}
                    ]
     :intervals ["2016-04-28T00:00:00.000Z/2016-04-30T00:00:00.000Z"]})

  (def q2
    {:queryType :timeseries
     :dataSource "dev.supercell"
     :granularity :minute
     :filter {
              :type :and
              :fields [{:type :selector :dimension "project_id" :value "vigiglobe-Earthquake"}
                       {:type :selector :dimension "source" :value "twitter"}
                       #_{:type :javascript :dimension "sex" :function "function(x) {return x != null;}"}]}
     :aggregations [#_{:type :cardinality :name "foo" :fieldNames ["authors"] :byRow false}
                    {:type :count :name "volume"}
                    ]
     :intervals ["2016-04-28T00:00:00.000Z/2016-04-30T00:00:00.000Z"]})

  (query "http://m1.vigiglo.be:8085/druid/v2/?pretty" q2))