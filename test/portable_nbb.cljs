(ns portable-nbb
  "The ClojureScript half of this library's test suite.

  `clojure -M:test` runs every namespace here on the JVM. Until this file
  existed, that was the ONLY runtime that had ever executed them — and most of
  this library is `.cljc`, so the extension was claiming a portability nothing
  checked. A `.cljc` file one runtime ever runs is a `.clj` file with a longer
  name.

  That is not hypothetical here. `langchain.edn-persist` was the last `.clj` in
  the runtime surface, and it is what held `cloud.itonami.app.store` — and
  everything waiting on it — on the JVM. Making it portable without a runner
  that executes it would have moved the claim, not the fact.

  Listed namespaces are the ones whose `:cljs` branch is real. A namespace that
  needs a JVM host stays out rather than being stubbed in."
  (:require [cljs.test :as t]
            [langchain.edn-persist-portable-test]))

(defmethod t/report [::t/default :end-run-tests] [m]
  (println "\nportable cljc —" (:test m) "tests,"
           (+ (:pass m) (:fail m) (:error m)) "assertions,"
           (:fail m) "failures," (:error m) "errors, on ClojureScript")
  (when-not (t/successful? m)
    (set! (.-exitCode js/process) 1)))

(t/run-tests 'langchain.edn-persist-portable-test)
