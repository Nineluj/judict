(ns judict.domain)

(def translations
  {:o "major/big"
   :ko "minor/small"
   :uki "floating"
   :goshi "hip"
   :ippon "one point"
   :seoi "shoulder"
   :nage "throw"
   :tai "body"
   :otoshi "drop"
   :de "advancing"
   :ashi "foot"
   :barai "sweep"
   :tsuri "lifting"
   :komi "pulling in"
   :uchi "inner"
   :soto "outer"
   :gari "reap"

   ;; Hold downs
   :kesa "scarf"
   :gatame "hold"
   :mune "chest"
   :kuzure "modified"
   :yoko "side"
   :shiho "four corner"
   :kami "upper"
   :tate "straight"

   ;; Breakfalls
   :ushiro "backwards"
   :mae "forwards"
   :ukemi "breakfall"
   :zempo "forward"
   :kaiten "rolling"

   ;; Other
   :tani "valley"
   :ura "back"})

(def techniques
  [ ;; Throwing Techniques (Tachi-waza)
   [:o :goshi]
   [:ippon :seoi :nage]
   [:uki :goshi]
   [:tai :otoshi]
   [:de :ashi :barai]
   [:tsuri :komi :goshi]
   [:ko :uchi :gari]
   [:ko :soto :gari]
   [:o :uchi :gari]
   [:o :soto :gari]

   ;; Holding Techniques (Osae-komi-waza)
   [:kesa :gatame]
   [:mune :gatame]
   [:kuzure :kesa :gatame]
   [:yoko :shiho :gatame]
   [:kami :shiho :gatame]
   [:tate :shiho :gatame]

   ;; Breakfalling (Ukemi)
   [:ushiro :ukemi]
   [:yoko :ukemi]
   [:mae :ukemi]
   [:zempo :kaiten :ukemi]

   ;; Groundwork Turning (Ne-waza)
   [:mune :gatame]
   [:tani :otoshi]
   [:ura :nage]])

(def categories
  (distinct (flatten techniques)))

(defn find-related-techniques [selected-items]
  (->> techniques
       (filter #(every? (set %) selected-items))
       flatten
       (into #{})
       (#(apply disj % selected-items))
       sort))

(defn get-full-technique-name [selection]
  (let [selected-keys (keys selection)
        matching-technique (->> techniques
                              (filter #(= (set %) (set selected-keys)))
                              first)]
    (if matching-technique
      (into {} (map #(vector % true) matching-technique))
      selection)))
