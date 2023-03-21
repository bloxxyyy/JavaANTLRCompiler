package nl.han.ica.datastructures;


import java.util.Optional;

public class HanLinkedList<T> implements IHANLinkedList<T> {

    private final Node<T> head;
    private int size;

    public HanLinkedList() {
        head = new Node<T>(null);
        size = 0;
    }

    @Override
    public void addFirst(T value) {
        Node<T> node = new Node<T>(value);
        node.setNext(head.getNext());
        head.setNext(node);
        size++;
    }

    @Override
    public void clear() {
        head.setNext(null);
        size = 0;
    }

    @Override
    public void insert(int index, T value) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }

        Node<T> prev = head;
        for (int i = 0; i < index; i++) {
            prev = prev.getNext();
        }

        Node<T> node = new Node<T>(value);
        node.setNext(prev.getNext());
        prev.setNext(node);
        size++;
    }

    @Override
    public void delete(int pos) {
        if (pos < 0 || pos >= size) {
            throw new IndexOutOfBoundsException();
        }

        Node<T> prev = head;
        for (int i = 0; i < pos; i++) {
            prev = prev.getNext();
        }

        prev.setNext(prev.getNext().getNext());
        size--;
    }

    @Override
    public T get(int pos) {
        if (pos < 0 || pos >= size) {
            throw new IndexOutOfBoundsException();
        }

        Node<T> node = head.getNext();
        for (int i = 0; i < pos; i++) {
            node = node.getNext();
        }

        return node.getValue();
    }

    @Override
    public void removeFirst() {
        if (head.getNext() == null) {
            throw new IndexOutOfBoundsException();
        }

        head.setNext(head.getNext().getNext());
        size--;
    }

    @Override
    public T getFirst() {
        if (head.getNext() == null) {
            throw new IndexOutOfBoundsException();
        }

        return head.getNext().getValue();
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public void addLast(T value) {
        Node<T> node = new Node<T>(value);
        Node<T> last = head;
        while (last.getNext() != null) {
            last = last.getNext();
        }
        last.setNext(node);
        size++;
    }

    private static class Node<T> {
        private T value;
        private Node<T> next;

        public Node(T value) {
            this.value = value;
            this.next = null;
        }

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }

        public Node<T> getNext() {
            return next;
        }

        public void setNext(Node<T> next) {
            this.next = next;
        }
    }
}
