(ns judict.events
  (:require
   [re-frame.core :as rf]
   [judict.db :as db]
   [judict.domain :as domain]))

(defn init-or-reset-db []
  (-> db/default-db
      (assoc :selectable (sort domain/categories))
      (assoc :is-full-technique nil)))

(rf/reg-event-db
 ::reset
 (fn [_ _] (init-or-reset-db)))

(rf/reg-event-db
 ::initialize-db
 (fn [_ _] (init-or-reset-db)))

(defn update-db-for-selection [db selection]
  (let [matching-techniques (domain/techniques-containing selection)
        full-match? (some #(= (count selection) (count %)) matching-techniques)]
    (print (str "is full match? " full-match?))
    (-> db
        (assoc :is-selection-valid full-match?)
        (assoc :selection (if full-match?
                              (first matching-techniques)
                              selection))
        (assoc :selectable (domain/techniques-without-selection matching-techniques selection)))))

(rf/reg-event-db
 ::select-item
 (fn [db [_ item]]
   (update-db-for-selection db (cons item (:selection db)))))

(rf/reg-event-db
 ::remove-item-from-selection
 (fn [db [_ item]]
   (update-db-for-selection db (remove #(= item %) (:selection db)))))
