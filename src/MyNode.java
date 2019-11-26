public class MyNode<T> implements Node<T> {

    private T value;
    Node<T> prev;
    Node<T> next;

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public void setValue(T n) {
        value = n;
    }

    @Override
    public void setNext(Node n) {
        next = n;
    }

    @Override
    public void setPrev(Node n) {
        prev = n;
    }

    @Override
    public Node<T> getNext() {
        return next;
    }

    @Override
    public Node<T> getPrev() {
        return prev;
    }

    public static void main(String[] args) {
        MyNode<Integer> node = new MyNode<>();
        node.setValue(1);

        MyNode<Integer> node2 = new MyNode<>();
        node.setValue(2);
        node.setPrev(node);

    }
}
