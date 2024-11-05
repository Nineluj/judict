(ns judict.events
  (:require
   [re-frame.core :as rf]
   [judict.db :as db]
   [judict.domain :as domain]))

(defn init-or-reset-db []
  (-> db/default-db
      (assoc :selectable (sort domain/categories))))

(rf/reg-event-db
 ::reset
 (fn [_ _] (init-or-reset-db)))

(rf/reg-event-db
 ::initialize-db
 (fn [_ _] (init-or-reset-db)))

(rf/reg-event-db
 ::select-item
 (fn [db [_ item]]
   (let [new-selection (assoc (:selection db) item true)
         related-items (domain/find-related-techniques (keys new-selection))]
       (-> db
           (#(assoc % :selection
                    (if (empty? related-items)
                      (domain/get-full-technique-name new-selection)
                      new-selection)))
           (assoc :selectable related-items)))))
