(ns xl.ev.aws)

(def db-schema [{:db/ident :aws/arn
                    :db/valueType :db.type/string
                    :db/cardinality :db.cardinality/one
                    :db/doc "AWS resource arn"}

                   {:db/ident :aws/resource-type
                    :db/valueType :db.type/string
                    :db/cardinality :db.cardinality/one
                    :db/doc "AWS resource type"}

                   {:db/ident :aws/resource-name
                    :db/valueType :db.type/string
                    :db/cardinality :db.cardinality/one
                    :db/doc "AWS resource name"}])