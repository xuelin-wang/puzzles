package xl.learn;

import java.util.Stack;

/**
 * Created by xuelin on 9/14/17.
 * choice to add cost to enqueue or dequeue (here add to dequeue)
 */
public class QueueWithTwoStacks<T> {
    private final Stack<T> s1 = new Stack<>();
    private final Stack<T> s2 = new Stack<>();

    public QueueWithTwoStacks(){}

    public void enqueu(T item) {
        s1.push(item);
    }

    public T deque() {
        if (s2.empty() && s1.empty())
            return null;
        if (s2.empty()) {
            s2.push(s1.pop());
        }
        return s2.pop();
    }
}
