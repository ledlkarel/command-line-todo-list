(ns command-line-todo-list.core
  (:gen-class))

(defn menu
  "Menu visualisation"
  []
  (println "\n")
  (println "=== TODO APP ===")
  (println "\n")
  (println "1. New Task")
  (println "2. View Tasks")
  (println "3. Compelte Task")
  (println "4. Delete Task")
  (println "5. Quit"))

(defn selector
  "Menu selector"
  [input]
  (cond
    (= input "1") (println "New Task")
    (= input "2") (println "View Tasks")
    (= input "3") (println "Compelte Task")
    (= input "4") (println "Delete Task")
    (= input "5") (println "Quit")
    :else (println "Input not supperted")))

(defn -main
  []
  (def quit-input (atom "0"))
  (while (not= @quit-input "5")
    (menu)
    (def input (read-line))
    (selector input)
    (reset! quit-input input)))

