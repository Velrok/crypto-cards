(ns crypto-cards.display
  (:require
    [crypto-cards.utils :as utils]))



(defn hand
  [player-name game]
  (:hand (utils/find-player-by-name player-name game)))

(defn draw-pile
  [player-name game]
  (:draw-pile (utils/find-player-by-name player-name game)))

(defn discard-pile
  [player-name game]
  (:discard-pile (utils/find-player-by-name player-name game)))
