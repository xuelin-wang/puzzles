## Amazon's "Customers who bought this item also bought" recommendation system

### Goal
From a product A, find out list of products other buyers of A 
also bought.

* naive

    * Every customer i has a list of bought products Si
    * Products categorized. Only products in same/related categories
should be in recommendation list. Or products' category connection has eight.
    * Each product p has list of Sj which includes p. Count 
each product's set count in Sj. Order by counts is
 the recommendations.
    * Recent purchases weighed more.

* Effecient Implementation
    * Updated with each purchase: Customer c's recent purchased products
    list order by date. Each of those product pj, update weight 
        * pij = f(w(cij), dt, pijOld)
        
        
* Analysis
    * categories: 10k
    * products: 10mm
    * pij: Only in same customer's list, and truncate to 20 per prod.        
200mm.   


### topic appears in comments
* bloom filter: space efficient. each element is mapped to k bits using
k hash functions (or a hash functions with k different output ranges, or 
orther techniques such as double hashing). Constant time to check membership:
 if all k bits of an elements' hash indices are 1. but can have false positives.
* count min sketch. Each has function map to cell in its row. count(i) =
min all rows r: count(r, h(r)(i)).
