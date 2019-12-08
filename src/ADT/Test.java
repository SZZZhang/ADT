package ADT;

import ADT.MyLListQueue;
import ADT.MyNode;

public class Test {

    public static void main(String[] args) {
        MyLListQueue queue = new MyLListQueue();

        MyNode<Integer> n = new MyNode<>();
        n.setValue(new Integer(1));
        queue.enqueue(n);
        MyNode n1 = new MyNode();
        n1.setValue(new Integer(2));
        queue.enqueue(n1);

        System.out.print(queue.peek());
        queue.dequeue();
    }

}
