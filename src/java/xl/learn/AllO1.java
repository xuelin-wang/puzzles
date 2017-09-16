package xl.learn;

/**
 *
 * Implement a data structure supporting the following operations:

 Inc(Key) - Inserts a new key with value 1. Or increments an existing key by 1.
 Key is guaranteed to be a non-empty string.
 Dec(Key) - If Key's value is 1, remove it from the data structure.
 Otherwise decrements an existing key by 1. If the key does not exist, this function does nothing.
 Key is guaranteed to be a non-empty string.
 GetMaxKey() - Returns one of the keys with maximal value.
 If no element exists, return an empty string "".
 GetMinKey() - Returns one of the keys with minimal value.
 If no element exists, return an empty string "".
 Challenge: Perform all these in O(1) time complexity.

 solution:
 constant by key, constant by value

 hash by key:
     valBucket: value, count, keys, prevBucket, nextBucket
   ConstOps {
     Map<String, Integer> kvs;
     Map<Integer, Bucket> countBuckets;
     Bucket head, tail;
   }

 Inc(Key) - kvs.get(key), put(key, 1) | not exists, otherwise put(key, inc(curval)). countBuckets[oldval]--, countBuckets[newVal]++.
     remove from valBuckets when count to zero. adjust orderedCounts with new change.
 Dec(Key) - If Key's value is 1, remove it from the data structure.
 Otherwise decrements an existing key by 1. If the key does not exist, this function does nothing.
 Key is guaranteed to be a non-empty string.
 GetMaxKey() - Returns one of the keys with maximal value.
 If no element exists, return an empty string "".
 GetMinKey() - Returns one of the keys with minimal value.
 If no element exists, return an empty string "".


 */

public class AllO1 {
}
