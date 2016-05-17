(ns arduino-test.sos
  (:use :reload-all clodiuno.core)
  (:use :reload-all clodiuno.firmata))

(def short-pulse 100)
(def long-pulse 200)
(def letter-delay 500)

(def letter-s [0 0 0])
(def letter-o [1 1 1])

(defn blink [board time]
  (digital-write board 13 HIGH)
  (Thread/sleep time)
  (digital-write board 13 LOW)
  (Thread/sleep time))

(defn blink-letter [board letter]
  (doseq [i letter]
    (if (= i 0)
      (blink board short-pulse)
      (blink board long-pulse)))
  (Thread/sleep letter-delay))

(defn sos []
  (let [board (arduino :firmata "/dev/ttyUSB0")]
    ;;allow arduino to boot
    (Thread/sleep 5000)
    (pin-mode board 13 OUTPUT)

    (blink-letter board letter-s)
    (blink-letter board letter-o)
    (blink-letter board letter-s)

    (close board)))
