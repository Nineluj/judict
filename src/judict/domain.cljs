(ns judict.domain
  [:require
   [clojure.string]])

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
   :kesa "scarf"
   :gatame "hold"
   :mune "chest"
   :kuzure "modified"
   :yoko "side"
   :shiho "four corner"
   :kami "upper"
   :tate "straight"
   :ushiro "backwards"
   :mae "forwards"
   :ukemi "breakfall"
   :zempo "forward"
   :kaiten "rolling"
   :tani "valley"
   :ura "back"
   :morote "both hands"
   :eri "lapel"
   :hiza "knee"
   :guruma "wheel"
   :sasae "supporting"
   :hane "spring"
   :okuri "sliding"
   :mata "thigh"
   :harai "sweeping"
   :gake "hook"
   :mawari "turning"
   :waki "armpit"
   :juji "cross"
   :ude "arm"})

;; syllabus is here:
;; https://www.britishjudo.org.uk/get-started/grading/kyu-grade-scheme/
(def syllabus
  {
   :red
   {:breakfalls '((ushiro ukemi)
                  (yoko ukemi)
                  (mae mawari ukemi))
    :tachi-waza '((o soto gari)
                  (de ashi barai)
                  (uki goshi))
    :osaekomi-waza '((kesa gatame)
                     (mune gatame)
                     (kuzure kesa gatame))}
   :yellow
   {:breakfalls '((mae ukemi))
    :tachi-waza '((tai otoshi)
                  (ippon seoi nage)
                  (o uchi gari))
    :osaekomi-waza '((yoko shiho gatame)
                     (tate shiho gatame)
                     (kami shiho gatame))}

   :orange
   {:tachi-waza '((tsuri komi goshi)
                  (o goshi)
                  (seoi otoshi)
                  (morote seoi nage)
                  (ko uchi gari)
                  (ko soto gake)
                  (ko soto gari)
                  (osoto gari))}

   :green
   {:tachi-waza '((harai goshi)
                  (uchi mata)
                  (hiza guruma)
                  (sasae tsuri komi ashi)
                  (hane goshi)
                  (okuri ashi barai)
                  (morote eri seoi nage))
    :kansetsu-waza '((ude gatame)
                     (waki gatame)
                     (hiza gatame)
                     (juji gatame))}})

(def techniques
  (->> syllabus
       vals
       (mapcat vals)
       (mapcat identity)
       (map #(map keyword %))))

(def categories
  (->> techniques
       flatten                       ; flatten the individual technique keywords
       distinct                         ; get unique words
       sort))              ; sort alphabetically


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
  (clojure.string/join " " (map name lst)))

(defn technique-link [selection]
  (let [technique (str "Judo " (join-keywords selection) " technique")]
    (str "https://www.youtube.com/results?search_query="
         (js/encodeURIComponent technique))))
