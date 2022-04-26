package DoublyLinkedList;

import java.util.Iterator;

public class ListIterator<T> implements Iterator<T> {

    private DListNode<T> currNode;
    private final DListNode<T> tail;
    private final DListNode<T> head;
    ListIterator(DListNode<T> header,DListNode<T> trailer) {
        this.currNode = header;
        this.tail = trailer;
        this.head = header;
    }

    @Override
    public boolean hasNext() {
        return currNode.next != tail;
    }

    @Override
    public T next() {
        currNode = currNode.next;
        return currNode.value;
    }

    @Override
    public void remove() {
        // removes the currNode
        if (currNode.prev == null || currNode.next == head) throw new IllegalStateException("cannot remove a header or a trailer node");
        else {
            currNode.prev.next = currNode.next;
            currNode.next.prev = currNode.prev;
        }
        // after next `next() call` currNode won't be referenced anymore and can be collected by garbage collector
    }

    public void removePrev() throws IllegalStateException {
        // removes the prevNode
        if (currNode.prev != null && currNode.prev != head) {
            currNode = currNode.prev;
            currNode.prev.next = currNode.next; // currNode.prev != null;
            currNode.next.prev = currNode.prev; // currNode.next != null
            currNode = currNode.next;
        }
        else throw new IllegalStateException("cannot remove a header node or a null node");
    }

    public void removeNext() throws IllegalStateException {
        // removes the nextNode
        if (currNode.next != null && currNode.next != tail) {
            currNode = currNode.next;
            currNode.prev.next = currNode.next; // currNode.next != null;
            currNode.next.prev = currNode.prev; // currNode.prev != null
            currNode = currNode.prev;
        }
        else throw new IllegalStateException("cannot remove a trailer node or a null node");
    }

    public void insert(T value) {
        // insert this value into the list before currNode
        DListNode<T> newNode = new DListNode<>(value);
        if (currNode.prev == null) throw new IllegalStateException("cannot add a node before header");
        else {
            // currNode.prev != null
            newNode.prev = currNode.prev;
            newNode.next = currNode;
            currNode.prev.next = newNode;
            currNode.prev = newNode;
        }
    }

}