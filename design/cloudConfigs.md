### Goal
* fetch cloud configurations of customers' accounts from cloud vendors.
    * fast - want to check/fetch quick and often
    * reliably - fetch correct data. Know if not accessible at the time.
    * change-tolerant - easy to evolve when cloud api/schema changes.
    * stingy - don't use too much resource: api quota, storage (only save diff?)
* store cloud configuration data
    * stingy - only save diff?
    * complete - save time series
    * easy to use - queriable for data check patterns
    
### Design
* Rest vs. SDK API for fetching
    * SDK API can go out of date. Rest is more up to date,
    * JSON is standardized. No need to convert to typed pojos, avoid serialization/deserializations.
    * Straightforward, know exactly the calls. SDK api call may trigger additional calls/processing.
    * One time coding for rest pattern. So not much harder than SDK.
    * Check against json. Fetch items from json, no need to convert the whole json to a pojo.
* Failure handling:
    * strategy. Leaning to simple ones: 
        * simply wait for next round.       
        * retry a couple of times
        * exponentially backoff (is this necessary?)
* Storage:
    * append log / immutable history?
    * to save memory, use custom diff function: ignore irrelevant items. Because naive diff not working: some config
    may have timestamp / items keep changing.
    * Such as: datomic? kafka? 
        * either one, will need a migration out of it to more permanent ones?
        * partition by account/time so can scale forever?

### Considerations
* Platform choices
    * ion datomic
    * rest api
    * java/clojure
    
* fetching
    * rest api. 
        * cloud vendor -> list of rest apis to cover all configuration data we care.
        * api is configuration: rest url spec.
        
* storage schema
    * try to be cloud vendor neutral, but can diff
    * shape
        * shallow tree
        * node is json (how to make querying on this easy? or just load into memory?)
    * json schema is automatecally derived from rest api return
    * automatically detect schema change
    * custom diff with configurable items to check/ignore.

* fetching scaling:
    * by account
    * by resource type + batch/pagination: only for types necessary.
        * VMs above tens of thousands?
        * Users above tens of thousands?
        * what else?
        
### Implementation
1. Design storage schema: shallow tree with json data leaf nodes.
2. Naive fetching through rest api and store into datomic.
3. Json check predicates 
    * Configuable? may need programming due to complex logic
    * cross nodes?
        * check across whole accounts: store expensive check results?
        * check aross resource types, 
        * for same resource type
        * for a resource
        * other?
3. rest api config
4. json diff config
5. json schema derived from actual sample json data retrieved + automatically validation json data.
6. diff json with checking items configurable.
     
### action items
1. GCP storage schema.
    * mirror gcp resource hierarchy
        * org / folders / resource type / resource id / data type / json
2. fetching via rest api.        
             
    