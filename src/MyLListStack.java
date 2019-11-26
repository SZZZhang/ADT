public class MyLListStack implements LListStack {

    MyLinkedList list = new MyLinkedList();

    @Override
    public void push(Node n) {
        list.addNode(n);
    }

    @Override
    public Node pop() {
        Node deletedNode = list.removeNode(list.size());
        return null;
    }

    @Override
    public Node peek() {
        return list.getLastNode();
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        if (list.size() == 0) return true;
        return false;
    }
}
