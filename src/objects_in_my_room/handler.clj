(ns objects-in-my-room.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.util.response :refer [response]]
            [ring.middleware.content-type :refer [wrap-content-type]]
            [objects-in-my-room.objects :as objs]
            [ring.middleware.json :refer [wrap-json-response]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(defn test-response []
  (fn [request]
    (response {:test "test" :wtf "WILLT HIS WORK???"})))

(defn test-response-json []
  (wrap-json-response (test-response)))

(defn room-object-handler [object]
  (fn[request]
    (response (eval object))))

(defn room-object-response-json [object]
  (wrap-json-response (room-object-handler object)))

(defroutes app-routes
  (GET "/:name" [name] (room-object-response-json (symbol "objects-in-my-room.objects" name)))
  (GET "/" [] (test-response-json))
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))

; (load-file "src/objects_in_my_room/handler.clj")