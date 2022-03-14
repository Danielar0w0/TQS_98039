package pt.ua.deti.tqs;

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class TqsStack<T> {

    private LinkedList<T> stack;
    private int limit;

    public TqsStack() {
        stack = new LinkedList<>();
        limit = -1;
    }

    public TqsStack(int limit) {
        stack = new LinkedList<>();
        this.limit = limit;
    }

    // Add an item on the top
    public void push(T x) {

        if (stack.size() == limit)
            throw new IllegalStateException();

        stack.push(x);
    }

    // Remove the item at the top
    public T pop() {
        return stack.pop();
    }

    // Return the item at the top (without removing it)
    public T peek() {

        if (stack.isEmpty())
            throw new NoSuchElementException();

        return stack.peek();
    }

    // Return the number of items in the stack
    public int size() {
        return stack.size();
    }

    // Return whether the stack has no items
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

}
