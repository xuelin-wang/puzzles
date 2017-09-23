# Tiny url

## Goal
Shorten urls

## Implementation considerations and issues
A url is encoded to a (fixed length?) short string. https://www.tinyurl.com/<encoded> is the shorter url. The mapping of <encoded> to original url is stored in database.

encoding method:
* auto id -> base 62 string.
* hash int -> base 62 string.
* rand int -> base 62 string.
* multiple rand int % 62 -> base 62 string. reduce likely conflicts

Should we expire older urls? To save space and increase performance. However, in case of conflicts, that will be an issue.

multiple databases, how to partition? By original url prefix?

Performance: Geo partition and replication of dbs?


## Questions
* How many unique identifiers possible? Will you run out of unique URLs?

Depends on how to encode. if it's a string less than or equals to 6, 62^6 = 56B. If used integer during encoding, max is range of integer = 4B.

* Should the identifier be increment or not? Which is easier to design? Pros and cons?

If use Sql db which provides auto id, then convenient. However: 1. is multiple dbs, need prefix byte(?) of partition id. Cons of id: easy to predict next urls. Pros: simple algorithm.

* Mapping an identifier to an URL and its reversal - Does this problem ring a bell to you?

Never need reversal query. Just encode and lookup, check if long url matches. But encoding conflict may cause performance issue.  

* How do you store the URLs? Does a simple flat file database work?

100M users, daily 1 read 0.1 write. total 100M r, 10M w. Thousands QPS.
1k * 4B = 4T. single disk to hold it.
But query performance?

* What is the bottleneck of the system? Is it read-heavy or write-heavy?

read-heavy likely, due to each written url will be clicked multiple times.
Read performance is also more important. Think about user scenario. When publishing, taking more time. Readers likely more in a hurry.
QPS: thousands can be handled by a single SQL server. However, peak traffic may be heavier, also need some extra capacity, so should have multiple dbs with partition.

* Estimate the maximum number of URLs a single machine can store.

1T / 1k = 1B.

* Estimate the maximum number of queries per second (QPS) for decoding a shortened URL in a single machine.

Mysql can handle thousands QPS.

* How would you scale the service? For example, a viral link which is shared in social media could result in a peak QPS at a moment's notice.
How could you handle redundancy? i,e, if a server is down, how could you ensure the service is still operational?

Memcache for recent used urls.
For redundancy, each partition need at least a backup, to increase reliability, even in different data centers. That will require cross-data center replication (asynchronous, eventual consistency).


* Keep URLs forever or prune, pros/cons? How we do pruning? (Contributed by @alex_svetkin)
prune
pros: less data help performance (small dataset to search), encoding perf (less conflicts likely).
cons: old shortened url may not be valid any more.

* What API would you provide to a third-party developer? (Contributed by @alex_svetkin)
```
String encode(String url)
String decode(String encoded)
#web layer
Get
Post
www.tinyurl.com/r/<endoded>
www.tinyurl.com/w/url
```
* If you can enable caching, what would you cache and what's the expiry time? (Contributed by @Humandroid)

map of encoded -> url. expiry time to be 1 hour or cache capacity reached. Mainly to handle small amount of "host" urls. Should not be many.
