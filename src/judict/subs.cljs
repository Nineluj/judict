(ns judict.subs
  (:require [re-frame.core :as rf]))

(rf/reg-sub
 :selectable
 (fn [db]
   (:selectable db)))

(rf/reg-sub
 :selection
 (fn [db]
   (:selection db)))
