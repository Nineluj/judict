(ns judict.styles)

(def colors
  {:primary "#087C12"      ; Office green
   :background "#F6F6F6"   ; Seasalt
   :text "#000000"         ; Black
   :accent "#FF5714"       ; Giants orange
   :secondary "#335C81"})  ; Lapis Lazuli

(defn technique [color has-on-click?]
  {:background color
   :padding "15px 25px"
   :border-radius "12px"
   :box-shadow "0 2px 4px rgba(0,0,0,0.1)"
   :cursor (when has-on-click? "pointer")
   :transition "transform 0.2s ease"
   :hover {:transform "scale(1.05)"}
   :color (:text colors)
   :font-weight "bold"})

(def selection-area
  {:display "flex"
   :flex-direction "row"
   :justify-content "center"
   :flex-wrap "wrap"
   :gap "20px"
   :margin "30px 0"
   :padding "20px"
   :background (:background colors)
   :border-radius "15px"})

(def reset-button
  {:background (:secondary colors)
   :color "white"
   :border "none"
   :padding "10px 20px"
   :border-radius "8px"
   :cursor "pointer"
   :font-weight "bold"
   :margin "20px auto"
   :display "block"
   :transition "transform 0.2s ease, opacity 0.2s ease"
   :hover {:transform "scale(1.05)"}
   :opacity 0.9})
