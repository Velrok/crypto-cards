(ns crypto-cards.actions
  (:require
    [crypto-cards.utils :as utils]))

(defn validate-card-count?
  [player]
  (let [deck-size (:deck-size player)
        counts (->> player
                    ((juxt :draw-pile :hand :discard-pile))
                    (map count))]
    (assert (= deck-size (apply + counts))
            (prn-str ["not =" :deck-size deck-size :counts counts])))
  true)

(defn valid-game-state?
  [game]
  (assert (validate-card-count? (:player game)))
  (assert (validate-card-count? (:oponent game)))
  true)

(defn draw-hand
  [game]
  {:pre  [(valid-game-state? game)]
   :post [(valid-game-state? %)]}
  (update-in
    game [:player]
    (fn [player]
      (let [hand-size 2
            draw-pile (:draw-pile player)]
        (if (< (count draw-pile) hand-size)
          ; not enoguh cards
          (do (println "not enogh cards")
              (let [new-draw-pile           (shuffle (:discard-pile player))
                    take-from-new-draw-pile (- hand-size (count draw-pile))
                    hand                    (into [] (concat draw-pile (take take-from-new-draw-pile)))]
                (-> player
                    (assoc :hand hand)
                    (assoc :draw-pile (drop take-from-new-draw-pile new-draw-pile))
                    (assoc :discard-pile []))))

          ; plenty of cards
          (do (println "plenty of cards")
              (let [hand (take hand-size draw-pile)]
                (-> player
                    (assoc :hand      hand)
                    (assoc :draw-pile (drop hand-size draw-pile))))))))))

(defn swap-player-and-oponent
  [game]
  {:pre  [(valid-game-state? game)]
   :post [(valid-game-state? %)]}
  (-> game
      (assoc :player  (:oponent game))
      (assoc :oponent (:player game))))

(defn end-turn
  [game]
  {:pre  [(valid-game-state? game)]
   :post [(valid-game-state? %)]}
  (-> game
      (update-in
        [:player]
        (fn [player]
          (let [new-discard-pile (into () (concat (:discard-pile player)
                                                  (:hand player)))]
            (-> player
                (assoc :discard-pile new-discard-pile)
                (assoc :hand ())))))
      swap-player-and-oponent))

(defn players-turn?
  [player game]
  (assert (= player (:player game)))
  true)

(defn card-in-hand?
  [player card]
  (assert
    (some #{card} (-> player :hand)))
  true)

(defn play-card
  [card game]
  {:pre [(valid-game-state? game)
         (card-in-hand? (:player game) card)]
   :post [valid-game-state? game]}
  (update-in game [:player]
             (fn [player]
               (-> player
                   (update-in [:hand] #(difference (set %)
                                                   #{card}))))))

(comment

  
  (let [[before it after] (partition-by #{:b}
                                        [:a :a :b :b :c :a])]
    )
  
  
  )
