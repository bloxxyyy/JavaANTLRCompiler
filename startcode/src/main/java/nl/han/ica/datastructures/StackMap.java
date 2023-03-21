package nl.han.ica.datastructures;

import java.util.Stack;

public class StackMap<T> implements IHANStack<T> {

    private int size;
    private Node<T> top;

    public static class Node<T> {
        private final T data;
        private Node<T> next;
        public Node(T data) {
            this.data = data;
        }
    }

    public boolean isEmpty() {
        return top == null;
    }

    public int size() {
        return size;
    }

    @Override
    public void push(T data) {
        Node<T> newNode = new Node<>(data);
        newNode.next = top;
        top = newNode;
        size++;
    }

    @Override
    public T pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        T data = top.data;
        top = top.next;
        size--;
        return data;
    }

    @Override
    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        return top.data;
    }
}
