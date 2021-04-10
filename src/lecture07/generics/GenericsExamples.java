package lecture07.generics;

import lecture07.serialization.Moderator;
import lecture07.serialization.User;

import java.util.ArrayList;

public class GenericsExamples {

    public static void main(String[] args) {

        MyGenericStack<Integer> stackOfInts
                = new MyGenericStackImplementation<>(3);
        stackOfInts.push(3);
        stackOfInts.push(4);
        stackOfInts.push(2);
        printStack(stackOfInts);

        MyGenericStack<String> stackOfStrings
                = new MyGenericStackImplementation<>(3);
        stackOfStrings.push("3");
        stackOfStrings.push("4");
        stackOfStrings.push("2");
        printStack(stackOfStrings);

        MyGenericStack<Moderator> stackOfModerators
                = new MyGenericStackImplementation<>(3);
        printUserStack(stackOfModerators);

        genericPush(stackOfStrings, "5");

        bug();
    }

    private static void printStack(MyGenericStack<?> stack) {
        while (!stack.isEmpty()) {
            System.out.println(stack.pop());
        }
    }

    private static void printUserStack(MyGenericStack<? extends User> stack) {
        while (!stack.isEmpty()) {
            System.out.println(stack.pop());
        }
    }

    private static <T> void genericPush(MyGenericStack<T> stack, T item) {
        stack.push(item);
    }

    private static void bug() {
        ArrayList arrayList = new ArrayList();
        arrayList.add("1");
        Integer item = (Integer) arrayList.get(0);
    }


}
