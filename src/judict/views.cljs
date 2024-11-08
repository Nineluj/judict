(ns judict.views
 (:require
  [re-frame.core :as rf]
  [judict.domain :as domain]
  [judict.styles :as style]))

(defn technique [name color on-click]
 [:div
  {:on-click on-click
   :key name
   :style (style/technique color on-click)}
  name])

(defn floating-text [name]
  (let [handler #(rf/dispatch [:judict.events/select-item name])]
    (technique name (:primary style/colors) handler)))

(defn translation-display [word]
  [:div
   {:style {:color (:secondary style/colors)
            :font-size "0.9em"
            :margin-top "5px"}}
   (str "\"" (get domain/translations word "?") "\"")])

(defn selected-technique [name]
  [:div
   {:style {:text-align "center"}
    :key name}
   (technique name (:accent style/colors) #(rf/dispatch [:judict.events/remove-item-from-selection name]))
   (translation-display name)])

(defn selection-area []
  (let [selectable (rf/subscribe [:selectable])]
    [:div
     {:style style/selection-area}
     (map floating-text @selectable)]))

(defn selection-display []
  (let [selection @(rf/subscribe [:selection])
        no-selection (empty? selection)
        full-selection @(rf/subscribe [:is-selection-valid])]
    [:div
     {:style {:background (:background style/colors)
              :padding "20px"
              :border-radius "15px"
              :margin "20px 0"}}
     [:div
      {:style {:display "flex"
               :flex-direction "row"
               :justify-content "center"
               :flex-wrap "wrap"
               :gap "20px"}}
      (map selected-technique selection)]

     (when no-selection
       [:p
        {:style {:text-align "center"
                 :color (:secondary style/colors)
                 :font-weight "bold"
                 :margin-top "15px"}}
        "Select a word above to get started!"])

     (when full-selection
      [:a {:href (domain/technique-link selection)
           :target "_blank"
           :style {:display "block"
                   :text-align "center"
                   :margin "20px 0"
                   :color (:accent style/colors)}}
       "View technique on YouTube"])]))

(defn reset-button []
  [:button
   {:on-click #(rf/dispatch [:judict.events/reset])
    :style style/reset-button}
   "Reset Selection"])

(defn main-panel []
  (let [has-selection (seq @(rf/subscribe [:selection]))
        has-selectable (seq @(rf/subscribe [:selectable]))]
    [:div
     {:style {:max-width "800px"
              :margin "0 auto"
              :padding "20px"
              :font-family "system-ui, -apple-system, sans-serif"}}
     [:h1
      {:style {:color (:text style/colors)
               :text-align "center"
               :font-size "2.5em"
               :margin-bottom "10px"}}
      "Judict"]
     (when has-selectable (selection-area))
     (selection-display)
     (when has-selection (reset-button))]))
