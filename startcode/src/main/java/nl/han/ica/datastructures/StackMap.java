package nl.han.ica.datastructures;

import java.util.Stack;

public class StackMap<T> implements IHANStack<T> {

    private final Stack<T> _Stack = new Stack<>();

    @Override
    public void push(T value) {
        _Stack.push(value);
    }

    @Override
    public T pop() {
        return _Stack.pop();
    }

    @Override
    public T peek() {
        return _Stack.peek();
    }
}
