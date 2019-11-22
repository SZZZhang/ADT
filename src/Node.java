public class Node implements Interfaces.Node {

    Object value;
    Interfaces.Node next;
    Interfaces.Node prev;

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public void setNext(Interfaces.Node n) {
        next = n;
    }

    @Override
    public void setPrev(Interfaces.Node n) {
        prev = n;
    }

    @Override
    public Interfaces.Node getNext() {
        return next;
    }

    @Override
    public Interfaces.Node getPrev() {
        return prev;
    }
}
