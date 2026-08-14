(ns command-line-todo-list.core
  (:gen-class)
  (:require [clojure.edn :as edn]))
(use 'clojure.java.io)

(def tasks (atom []))

(defn read-file 
  "Read from a file"
  [] 
   (try
     (reset! tasks (or (edn/read-string (slurp "tasks.txt")) []))
     (catch Exception e ())))

(defn new-line 
  "Create a new line"
  []
  (println "\n"))

(defn parse-int
  "Convert first number in a string in to a int"
  [s]
  (Integer. (re-find  #"\d+" s)))

(defn write-file
  "Write to file"
  []
  (with-open [wrtr (writer "tasks.txt")]
    (.write wrtr (str @tasks))))

(defn new-task
  "Create new task"
  []
  (println "Name of task:")
  (let [task-name (read-line)]
    (swap! tasks conj {:name task-name :complete false}))
  (write-file)
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
  (try
    (let [input (parse-int (read-line))]
      (swap! tasks update input assoc :complete true)
      (write-file)
      (println "Task completed!"))
    (catch Exception e (println "Number of task doesnt exist"))))

(defn delete-task
  "Delete task"
  []
  (view-tasks)
  (new-line)
  (try
    (let [input (parse-int (read-line))]
      (swap! tasks (fn [coll]
                     (into [] (concat (subvec coll 0 input)
                                      (subvec coll (inc input)))))))
    (write-file)
    (println "Task deleted!")
    (catch Exception e (println "Number of task doesnt exist"))))

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
  (read-file)
  (let [quit-input (atom "0")]
    (while (not= @quit-input "5")
      (menu)
      (new-line)
      (let [input (read-line)]
        (new-line)
        (menu-selector input)
        (reset! quit-input input)))))

