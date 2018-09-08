Whenever you expose a web service / api endpoint, you need to implement a rate limiter to prevent abuse of the service (DOS attacks).

Implement a RateLimiter Class with an isAllow method. Every request comes in with a unique clientID, deny a request if that client 
has made more than 100 requests in the past second.

### Goal
* Need a map from client Id to requests count for the last second

Analysis
* number of clients c can be potentially huge
* false negative is fine, but no false positives should be below threshold
* time updates. (refresh per second?)

Implementation:
* min count: m * d
say n = 1mm,
 Each entry contains:
     start seconds s (long), count c (int), 12 bytes
     
* if we can afford 12M memory for this:
    when a new request form client x come in at time t,
         m(x) = (s, c), if (t-s) >= 1: update c = 1, allow
         otherwise: if c >= 100, deny or c++, allow

* if we cannot afford the memory for this:
    1k * 5, 5 hashes (cpu performance!)
    
* distributed map:
    * multiple nodes: only limit per node. Or sticky session. Or global map.
    
    
        
