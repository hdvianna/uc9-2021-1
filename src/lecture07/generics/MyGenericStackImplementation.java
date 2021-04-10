package lecture07.generics;

public class MyGenericStackImplementation<E> implements MyGenericStack<E> {

    private int size;
    private Object[] stack;
    private int tip = -1;

    public MyGenericStackImplementation(int size) {
        this.size = size;
        stack = new Object[this.size];
    }

    @Override
    public void push(E item) {
        if (tip < size - 1) {
            tip++;
            stack[tip] =  (Object) item;
        } else {
            throw new RuntimeException("Stack overflow!");
        }
    }

    @Override
    public E pop() {
        if (!isEmpty()) {
            Object item = stack[tip];
            tip--;
            return (E) item;
        } else {
            throw new RuntimeException("Empty stack!");
        }
    }

    @Override
    public boolean isEmpty() {
        return tip == -1;
    }
}
