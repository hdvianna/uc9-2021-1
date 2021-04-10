package lecture07.generics;

public interface MyGenericStack<E> {
    public void push(E item);
    public E pop();
    public boolean isEmpty();
}
