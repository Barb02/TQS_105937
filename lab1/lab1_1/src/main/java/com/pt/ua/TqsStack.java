package com.pt.ua;

import java.util.LinkedList;

public class TqsStack<T> {

    LinkedList<T> stack;

    public TqsStack() {
        stack = new LinkedList<T>();
    }

    public T pop() {
        return null;
    }
    
    public int size() {
        return stack.size();
    }

    public T peek() {
        return null;
    }

    public void push(T element) {

    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }
}