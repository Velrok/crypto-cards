(ns crypto-cards.utils)

(defn find-player-by-name
  [player-name game]
  (->> [(:player game) (:oponent game)]
       (filter #(= player-name (:name %)))
       first))
