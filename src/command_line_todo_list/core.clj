(ns command-line-todo-list.core
  (:gen-class))

(def tasks (atom []))

(defn new-line []
  (println "\n"))

(defn parse-int [s]
  (Integer. (re-find  #"\d+" s)))

(defn new-task
  "Create new task"
  []
  (println "Name of task:")
  (let [task-name (read-line)]
    (swap! tasks conj {:name task-name :complete false}))
  (println "Task added!"))

(defn view-tasks
  "Display all tasks"
  []
  (println "Tasks:")
  (doseq [[index item] (map-indexed vector @tasks)]
    (println
     index "-" (get item :name)
     (if
      (= (get item :complete) false) "✗" "✓"))))

(defn complete-task
  "Compelet task"
  []
  (view-tasks)
  (new-line)
  (let [input (parse-int (read-line))]
    (swap! tasks update input assoc :complete true))
  (println "Task completed!"))

(defn delete-task
  "Delete task"
  []
  (view-tasks)
  (new-line)
  (let [input (parse-int (read-line))]
    (swap! tasks (fn [coll]
      (concat (subvec coll 0 input)
              (subvec coll (inc input))))))
  (println "Task deleted!"))

(defn menu
  "Menu visualisation"
  []
  (println "\n")
  (println "=== TODO APP ===")
  (println "\n")
  (println "1. New Task")
  (println "2. View Tasks")
  (println "3. Complete Task")
  (println "4. Delete Task")
  (println "5. Quit"))

(defn menu-selector
  "Menu selector"
  [input]
  (cond
    (= input "1") (new-task)
    (= input "2") (view-tasks)
    (= input "3") (complete-task)
    (= input "4") (delete-task)
    (= input "5") (println "Quit\n")
    :else (println "Input not supperted\n")))

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

