(ns crypto-cards.core
  (:require
    [crypto-cards.actions :as actions]
    [crypto-cards.objects :as objects]
    [crypto-cards.display :as display]))

(defonce game (atom (objects/new-game)))



(comment

  (do (reset! game (actions/new-game))
      :OK)

  (let [player-name  "red"
        hand (-> (objects/new-game)
                 actions/draw-hand
                 (display/hand player-name))
        first-card (first hand)]
    (->> game
        (actions/play-card first-card)
        (juxt (partial display/draw-pile    player-name)
              (partial display/hand         player-name)
              (partial display/discard-pile player-name))))

  (clojure.pprint/pprint @game)
  (swap! game draw-hand)

  (clojure.pprint/pprint (display/hand @game "red"))


  )
