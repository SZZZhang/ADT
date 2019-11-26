public class MyLinkedList implements LinkedList {

    private MyNode head;
    private MyNode tail;
    private int size = 0;

    public MyLinkedList() {
        head.setNext(tail);
        tail.setPrev(head);
    }

    @Override
    public void addNode(Node n) {
        n.setPrev(tail.getPrev());
        tail.setPrev(n);
        size++;
    }

    @Override
    public void insertNode(Node n, int i) {
        n.setPrev(getNode(i));
        n.setNext(getNode(i).getNext());
        getNode(i).setNext(n);
    }

    @Override
    public void removeNode(Node n) {
        n.getPrev().setNext(n.getNext());
        n.getNext().setPrev(n.getPrev());
    }

    @Override
    public Node removeNode(int i) {
        Node node = getNode(i);
        node.getPrev().setNext(node.getNext());
        node.getNext().setPrev(node.getPrev());
        return node;
    }

    @Override
    public Node getNode(int i) {
        Node currentNode = head.getNext();

        for (int j = 0; j < size; j++) {
            if (j == i) {
                return currentNode;
            }
            currentNode = currentNode.getNext();
        }

        return null;
    }

    @Override
    public Node getFirstNode() {
        return head.getNext();
    }

    @Override
    public Node getLastNode() {
        return tail.getPrev();
    }

    @Override
    public int size() {
        return size;
    }
}
