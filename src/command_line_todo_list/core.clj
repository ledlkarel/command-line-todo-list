(ns command-line-todo-list.core
  (:gen-class))

(def tasks (atom []))

(defn new-task []
  (println "Name of task:")
  (let [task-name (read-line)]
    (swap! tasks conj {:name task-name :complete false}))
  (println "Task added!"))

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

(defn menu-selector
  "Menu selector"
  [input]
  (cond
    (= input "1") (new-task)
    (= input "2") (println "View Tasks\n")
    (= input "3") (println "Compelte Task\n")
    (= input "4") (println "Delete Task\n")
    (= input "5") (println "Quit\n")
    :else (println "Input not supperted\n")))

(defn new-line []
  (println "\n"))

(defn -main
  []
  (let [quit-input (atom "0")]
    (while (not= @quit-input "5")
      (menu)
      (new-line)
      (let [input (read-line)]
        (new-line)
        (menu-selector input)
        (reset! quit-input input)))))

