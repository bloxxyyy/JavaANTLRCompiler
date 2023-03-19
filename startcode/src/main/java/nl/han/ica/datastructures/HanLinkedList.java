package nl.han.ica.datastructures;

import java.util.LinkedList;

public class HanLinkedList<T> implements IHANLinkedList<T> {

    LinkedList<T> linkedList = new LinkedList<>();

    @Override
    public void addFirst(T value) {
        linkedList.addFirst(value);
    }

    @Override
    public void clear() {
        linkedList.clear();
    }

    @Override
    public void insert(int index, T value) {
        linkedList.add(index, value);
    }

    @Override
    public void delete(int pos) {
        linkedList.remove(pos);
    }

    @Override
    public T get(int pos) {
        return linkedList.get(pos);
    }

    @Override
    public void removeFirst() {
        linkedList.removeFirst();
    }

    @Override
    public T getFirst() {
        return linkedList.getFirst();
    }

    @Override
    public int getSize() {
        return linkedList.size();
    }
}
