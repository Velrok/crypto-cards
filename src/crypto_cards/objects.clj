(ns crypto-cards.objects)

(defn new-card
  [attr]
  (merge {:title  "Unnamed"
          :block  0
          :attack 0}
         attr))

(def defend (new-card {:title "Defend"
                       :block 5}))

(def strike (new-card {:title "Strike"
                       :attack 6}))

(def standard-deck
  [defend defend defend
   strike strike strike strike])

(defn new-player
  [name card-deck]
  {:name         name
   :current-energy 0
   :current-block 0
   :hp           100
   :deck-size    (count card-deck)
   :draw-pile    (shuffle card-deck)
   :hand         ()
   :discard-pile ()})

(defn new-game
  []
  (let [player-one (new-player "red"  standard-deck)
        player-two (new-player "blue" standard-deck)]
    {:player       player-one
     :oponent      player-two
     :turn-count   0
     :state        :init}))
