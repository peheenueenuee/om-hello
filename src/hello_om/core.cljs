(ns hello-om.core
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [sablono.core :as sab]))

(enable-console-print!)

(println "This text is printed from src/hello-om/core.cljs. Go ahead and edit it and see reloading in action.")

;; define your app data so that it doesn't get over-written on reload

(defonce app-state (atom {:links 0}))

;; (defn like-saymore [data]
;;   (sab/html [:div
;;              [:h1 "いいねの数 " (:links @data)]
;;              [:div [:a {:href "#"
;;                         :onclick #(swap! data update-in [:links] inc)}
;;                     "いいね!"]]]))
;; (defn render! []
;;   (.render js/React
;;            (like-saymore app-state)
;;            (.getElementById js/document "app")))
;; (add-watch app-state :on-change (fn [_ _ _ _] (render!)))

;; (render!)

(defn fizzbazz [n]
  (cond (= 0 (mod n 3)) (if (= 0 (mod n 5))  "fizzbazz" "fizz")
        (= 0 (mod n 5)) "bazz"
        :else n))

(defn put-dots [arg-lis]
  (defn iter [data-inner]
    (if (= data-inner '())
      '()
      (cons (dom/div nil (first data-inner)) (iter (rest data-inner)))))
  (dom/div nil (iter arg-lis)))

(om/root
  (fn [data owner]
    (reify om/IRender
      (render [_]
        (dom/div nil
                 (put-dots (map fizzbazz (range 1 30)))
                 (dom/div nil (dom/div nil "test1"))
                 ))))
  app-state
  {:target (. js/document (getElementById "app"))})

(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)
