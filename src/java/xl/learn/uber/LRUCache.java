package xl.learn.uber;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xuelin on 9/19/17.
 *
 * Design and implement a data structure for Least Recently Used (LRU) cache. It should support the following operations: get and put.

 get(key) - Get the value (will always be positive) of the key if the key exists in the cache, otherwise return -1.
 put(key, value) - Set or insert the value if the key is not already present. When the cache reached its capacity, it should invalidate the least recently used item before inserting a new item.

 Follow up:
 Could you do both operations in O(1) time complexity?

 Example:

 LRUCache cache = new LRUCache( 2 // capacity // );

        cache.put(1, 1);
        cache.put(2, 2);
        cache.get(1);       // returns 1
        cache.put(3, 3);    // evicts key 2
        cache.get(2);       // returns -1 (not found)
        cache.put(4, 4);    // evicts key 1
        cache.get(1);       // returns -1 (not found)
        cache.get(3);       // returns 3
        cache.get(4);       // returns 4
 */
public class LRUCache {
    public static class Item {
        Item accessLeft;
        Item accessRight;
        int key;
        int val;
        public Item(int key, int val, Item al, Item ar) {
            this.key = key;
            this.val = val;
            this.accessLeft = al;
            this.accessRight = ar;
        }
    }
    Item accessHead;
    Map<Integer, Item> kvs;
    int capacity;

    public LRUCache(int capacity) {
        kvs = new HashMap<>(capacity);
        accessHead = new Item(-1, -1, null, null);
        accessHead.accessLeft = accessHead;
        accessHead.accessRight = accessHead;
        this.capacity = capacity;
    }

    private void mostRecent(Item item) {
        if (item.accessLeft != null) {
            item.accessLeft.accessRight = item.accessRight;
            item.accessRight.accessLeft = item.accessLeft;
        }
        item.accessLeft = accessHead;
        item.accessRight = accessHead.accessRight;
        accessHead.accessRight.accessLeft = item;
        accessHead.accessRight = item;

    }

    public int get(int key) {
        Item item = kvs.get(key);
        if (item == null)
            return -1;
        mostRecent(item);
        return item.val;
    }

    public void put(int key, int val) {
        Item item = kvs.get(key);
        if (item == null) {
            if (kvs.size() == this.capacity) {
                Item oldest = accessHead.accessLeft;
                kvs.remove(oldest.key);
                oldest.accessLeft.accessRight = oldest.accessRight;
                oldest.accessRight.accessLeft = oldest.accessLeft;
                oldest.accessLeft = null;
                oldest.accessRight = null;
            }
            item = new Item(key, val, null, null);
            kvs.put(key, item);
        }
        else {
            item.val = val;
        }
        mostRecent(item);
    }

    public static void main(String[] args) {
        LRUCache c = new LRUCache(2);
        c.put(1, 1);
        c.put(2, 2);
        System.out.println("key 1 val: " + c.get(1));

        c.put(3, 3);
        System.out.println("key 2 val: " + c.get(2));

        c.put(4,4);
        System.out.println("key 1 val: " + c.get(1));

        System.out.println("key 3 val: " + c.get(3));

        System.out.println("key 4 val: " + c.get(4));
    }

}
