package xl.learn;

import java.util.*;

/**
 * Created by xuelin on 10/15/17.
 *
 * Design and implement a data structure for Least Frequently Used (LFU) cache. It should support the following operations: get and put.

 get(key) - Get the value (will always be positive) of the key if the key exists in the cache, otherwise return -1.
 put(key, value) - Set or insert the value if the key is not already present. When the cache reaches its capacity,
 it should invalidate the least frequently used item before inserting a new item. For the purpose of this problem,
 when there is a tie (i.e., two or more keys that have the same frequency), the least recently used key would be evicted.

 Follow up:
 Could you do both operations in O(1) time complexity?

 Example:

 LFUCache cache = new LFUCache( 2 ); //2 is capacity

        cache.put(1, 1);
        cache.put(2, 2);
        cache.get(1);       // returns 1
        cache.put(3, 3);    // evicts key 2
        cache.get(2);       // returns -1 (not found)
        cache.get(3);       // returns 3.
        cache.put(4, 4);    // evicts key 1.
        cache.get(1);       // returns -1 (not found)
        cache.get(3);       // returns 3
        cache.get(4);       // returns 4

 Analysis:
Need maintain access counts for keys, hash for lookup.
Maintain a double linked list of node orber by counts. each link node contains count and sets of data nodes which have this count.
Also maintain a map from count to node sets with this count.
        */

public class LFUCache {
    public static class Data {
        public int val;
        public int count;
        public Data( int v, int count) {
            this.val = v;
            this.count = count;
        }
    }

    public static class Node {
        public Set<Integer> keys;
        public int count;
        public Node prev;
        public Node next;
        public Node(int count, Set<Integer> keys) {
            this.count = count;
            this.keys = keys;
        }
    }

    public Map<Integer, Data> keyData;
    public Map<Integer, Node> countToNode;
    public int capacity;
    Node header;
    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.keyData = new HashMap<>(capacity);
        this.countToNode = new HashMap<>(capacity);
        header = new Node(-1, null);
        header.prev = header.next = header;
    }

    private Integer getPurgeKey() {
        Node nodeToPurge = header.prev;
        if (nodeToPurge.keys == null)
            return null; //empty cache
        return nodeToPurge.keys.iterator().next();
    }

    private void remove(int key, boolean removeVal) {
        Data data = keyData.get(key);
        int count = data.count;
        if (removeVal)
            keyData.remove(key);

        Node node = countToNode.get(count);
        node.keys.remove(key);
        if (node.keys.isEmpty()) {
            countToNode.remove(count);
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }
    }

    private void add(int key, int val) {
        Data data = new Data(val, 1);
        keyData.put(key, data);

        Node node = countToNode.get(1);
        if (node == null) {
            Set<Integer> keys = new HashSet<>();
            keys.add(key);
            node = new Node(1, keys);
            countToNode.put(1, node);
            header.prev.next = node;
            node.prev = header.prev;
            header.prev = node;
            node.next = header;
        }
        else {
            node.keys.add(key);
        }
    }

    private void update(int key, int val, int count) {

    }

    public int get(int key) {
        Data kv = keyData.get(key);
        int retval;
        if (kv == null) {
            retval = -1;
        }
        else {
            retval = kv.val;
            int count = kv.count;
            Node node = countToNode.get(count);
            Set<Integer> keys = node.keys;
            keys.remove(key);
            Node prev, next;
            if (keys.size() == 0) {
                node.prev.next = node.next;
                node.next.prev = node.prev;
                prev = node.prev;
                next = node.next;
            }
            else {
                prev = node;
                next = node.next;
            }

            int newCount = count++;
            Node newNode = countToNode.get(newCount);
            if (newNode == null) {
                keys = new HashSet<>(3);
                newNode = new Node(newCount, keys);
                countToNode.put(newCount, newNode);
            }
            newNode.prev = prev;
            if (next.count != newCount) {
                next.prev = newNode;
                newNode.next = next;
            }
        }
        return retval;
    }

    public void put(int key, int val) {
        Data kv = keyData.get(key);
        if (kv == null) {
            kv = new Data(key, val);
            kv.count = 1;
            keyData.put(key, kv);
            Node node = countToNode.get(1);
            if (node == null) {
                Set<Integer> keys = new HashSet<>();
                keys.add(key);
                node = new Node(1, keys);
                node.next = header.next;
                header.next.prev = node;
                node.prev = header;
                header.next = node;
            }
            else {
                node.keys.add(key);
            }
        }
        else {
            kv.val = val;
            int oldCount = kv.count;
            Node node = countToNode.get(oldCount);
            Set<Integer> keys = node.keys;
            keys.remove(key);
            Node prev, next;
            if (keys.size() == 0) {
                countToNode.remove(oldCount);
                node.prev.next = node.next;
                node.next.prev = node.prev;

                prev = node.prev;
                next = node.next;
            }
            else {
                prev = node;
                next = node.next;
            }

            int newCount = oldCount + 1;
            Node newNode = countToNode.get(newCount);
            if (newNode == null) {
                Set<Integer> newKeys = new HashSet<>();
                newKeys.add(key);
                newNode = new Node(newCount, newKeys);
                countToNode.put(newCount, newNode);
                newNode.prev = prev;
                newNode.next = next;
                prev.next = newNode;
                next.prev = newNode;
            }
            else {
                newNode.keys.add(key);
            }
        }
    }

}
