package xl.learn.uber;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Created by xuelin on 9/19/17.
 */
public class KSortedList {
    public static class Node {
        int val;
        Node right;
        public Node(int val, Node right) {
            this.val = val;
            this.right = right;
        }
        public int getVal() {
            return val;
        }
    }

    public static Node sort(Node firstNode, int k1) {
        int k = k1 + 1;
        Node end = firstNode;
        PriorityQueue<Node> q = new PriorityQueue<>(k, Comparator.comparing(Node::getVal));

        Node newHead = null;
        Node newTail = null;
        while (end != null) {
            q.add(end);
            if (q.size() == k) {
                Node min = q.poll();
                if (newHead == null) {
                    newHead = min;
                    newTail = min;
                }
                else {
                    newTail.right = min;
                    newTail = min;
                }
            }
            end = end.right;
        }
        while (!q.isEmpty()) {
            Node min = q.poll();
            newTail.right = min;
            min.right = null;
            newTail = min;
        }
        if (newTail != null)
            newTail.right = null;
        return newHead;
    }

    private static Node createNodes(int[] ns) {
        Node head = null;
        Node tail = null;
        for (int n: ns) {
            if (head == null) {
                head = new Node(n, null);
                tail = head;
            }
            else {
                tail.right = new Node(n, null);
                tail = tail.right;
            }
        }
        return head;
    }

    private static void outputNodes(Node head) {
        Node tail = head;
        System.out.println();
        while (tail != null) {
            System.out.println(tail.val + ", ");
            tail = tail.right;
        }
        System.out.println();
    }

    public static void main(String [] args) {
        Node head = createNodes(new int[]{3,2,1,6,5,4});
        head = sort(head, 2);
        outputNodes(head);
    }
}
