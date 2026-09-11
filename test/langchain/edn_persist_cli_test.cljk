(ns langchain.edn-persist-cli-test
  (:require [clojure.java.io :as io]
            [clojure.test :refer [deftest is]]
            [langchain.edn-persist-cli :as cli])
  (:import [java.nio.file Files]
           [java.nio.file.attribute FileAttribute]))

(deftest non-clojure-bridge-round-trips-json-without-owning-edn
  (let [dir (.toFile (Files/createTempDirectory
                      "langchain-edn-bridge-"
                      (make-array FileAttribute 0)))
        file (io/file dir "state.edn")]
    (cli/execute "append" file "organism/did-1"
                 "{\"op\":\"state/put\",\"state\":{\"mass\":3,\"tick\":1}}")
    (cli/execute "append" file "organism/did-1"
                 "{\"op\":\"state/put\",\"state\":{\"mass\":4,\"tick\":2}}")
    (is (= {"op" "state/put" "state" {"mass" 4 "tick" 2} :seq 2}
           (cli/execute "latest" file "organism/did-1" "")))
    (is (= 2 (count (cli/execute "read" file "organism/did-1" ""))))))
