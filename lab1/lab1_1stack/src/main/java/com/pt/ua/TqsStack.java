package com.pt.ua;

import java.util.LinkedList;

public class TqsStack<T> {

    LinkedList<T> stack;

    public TqsStack() {
        stack = new LinkedList<T>();
    }

    public T pop() {
        return stack.pop();
    }
    
    public int size() {
        return stack.size();
    }

    public T peek() {
        return stack.element();
    }

    public void push(T element) {
        stack.push(element);
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }
}