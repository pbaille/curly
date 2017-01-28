(ns leiningen.new.curly
  (:require [leiningen.new.templates :refer [renderer name-to-path ->files]]
            [leiningen.core.main :as main]))

(def render (renderer "curly"))

(defn curly
  "template for figwheel-sidecar project (cursive)"
  [name]
  (let [data {:name name
              :sanitized (name-to-path name)}]
    (main/info "Generating fresh 'lein new' curly project.")
    (->files data
             ["README.md" (render "README.md" data)]
             [".gitignore" (render ".gitignore" data)]
             ["resources/public/index.html" (render "index.html" data)]
             ["script/fig.clj" (render "fig.clj" data)]
             ["project.clj" (render "project.clj" data)]
             ["src/{{sanitized}}/core.cljs" (render "core.cljs" data)]
             ["src/{{sanitized}}/styles/core.clj" (render "styles.clj" data)]
             ["workspace.xml" (render "workspace" data)])))
