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

(defn techniques-containing [words]
  (->> techniques
       (filter #(every? (set %) words))))

(defn techniques-without-selection [techniques selection]
  (->> techniques
       flatten
       distinct
       (remove (set selection))  ;; Use remove with selection set instead of filter
       sort))

(defn join-keywords [lst]
  (apply str (map name lst)))

(defn technique-link [selection]
  (let [technique (join-keywords selection)]
    (str "https://judoinfo.com/" technique)))
