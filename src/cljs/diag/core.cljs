(ns diag.core
  (:require
   [reagent.core :as reagent]
   [re-frisk.core :as rf]
   [devtools.core :as devtools]
   ;;
   [cljsjs.quill]))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Vars

(defonce debug?
  ^boolean js/goog.DEBUG)

(defonce state
  (reagent/atom {}))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Select dom

(defn fclass [node class]
  (aget (.getElementsByClassName node class) 0))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Quill

(defn quill []
  (reagent/create-class 
   {:component-did-mount
    (fn [this]
      (js/Quill. (reagent/dom-node this)
                 (clj->js {:theme "snow"})))
    :reagent-render
    (fn [] [:div.quill])}))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Page

(defn page [ratom]
  [:div.panel.panel-default
   [:h2 "Symptomes"]
   [:div.panel-body
    [quill]
    [:button..btn.btn-default "Send"]]])

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Initialize App

(defn dev-setup []
  (when debug?
    (enable-console-print!)
    (rf/enable-frisk!)
    (rf/add-data :app-state state)
    (println "dev mode")
    (devtools/install!)
    ))

(defn reload []
  (reagent/render [page state]
                  (.getElementById js/document "app")))

(defn ^:export main []
  (dev-setup)
  (reload))
