package DoublyLinkedList;

import java.util.Comparator;
import java.util.Iterator;

public class DList<T> implements Iterable<T> {
    // DList is 0 index-based
    private final DListNode<T> header; // head sentinel
    private final DListNode<T> trailer; // tail sentinel
    private int size;

    public DList() {
        this.header = new DListNode<>();
        this.trailer = new DListNode<>();
        this.header.next = this.trailer;
        this.trailer.prev = this.header;
        this.size = 0;
    }

    public void insertAtFirst(T value) {
        DListNode<T> node = new DListNode<>(value);
        node.next = this.header.next;
        this.header.next.prev = node;
        this.header.next = node;
        node.prev = this.header;
        size++;
    }

    public void insertAtLast(T value) {
        DListNode<T> node = new DListNode<>(value);
        node.next = this.trailer;
        node.prev = this.trailer.prev;
        this.trailer.prev.next = node;
        this.trailer.prev = node;
        size++;
    }

    public void insertAt(T value, int index) throws IndexOutOfBoundsException {
        if (index < 0) throw new IndexOutOfBoundsException();
        else {
            DListNode<T> currentNode = this.header.next;
            int i = 0;
            while (currentNode != trailer && i < index) {
                currentNode = currentNode.next;
                i++;
            }
            if (currentNode == trailer) throw new IndexOutOfBoundsException();
            else {
                // i = index
                DListNode<T> node = new DListNode<>(value);
                currentNode.prev.next = node;
                node.prev = currentNode.prev;
                currentNode.prev = node;
                node.next = currentNode;
                size++;
            }
        }
    }

    public boolean isEmpty() {
        return this.header.next == this.trailer && this.trailer.prev == this.header;
    }

    public int size() {
        return size;
//        int i = 0;
//        DListNode<T> currentNode = this.header.next;
//        while (currentNode != trailer) {
//            currentNode = currentNode.next;
//            i++;
//        }
//        return i;
    }

    public T deleteAtFirst() throws EmptyListException {
        if (this.isEmpty()) throw new EmptyListException();
        else {
            DListNode<T> currentNode = this.header.next;
            currentNode.prev.next = currentNode.next;
            currentNode.next.prev = currentNode.prev;
            currentNode.next = null;
            currentNode.prev = null;
            size--;
            return currentNode.value;
        }
    }

    public T deleteAtLast() throws EmptyListException {
        if (this.isEmpty()) throw new EmptyListException();
        else {
            DListNode<T> currentNode = this.trailer.prev;
            currentNode.prev.next = currentNode.next;
            currentNode.next.prev = currentNode.prev;
            currentNode.next = null;
            currentNode.prev = null;
            size--;
            return currentNode.value;
        }
    }

    public void deleteAt(int index) throws IndexOutOfBoundsException, EmptyListException {
        if (index < 0) throw new IndexOutOfBoundsException();
        else if (this.isEmpty()) throw new EmptyListException();
        else {
            int i = 0;
            DListNode<T> currentNode = this.header.next;
            while (currentNode != trailer && i < index) {
                currentNode = currentNode.next;
                i++;
            }
            if (currentNode == trailer) throw new IndexOutOfBoundsException();
            else {
                currentNode.prev.next = currentNode.next;
                currentNode.next.prev = currentNode.prev;
                currentNode.next = null;
                currentNode.prev = null;
                size--;
            }
        }
    }

    public T first() throws EmptyListException{
        if (this.isEmpty()) throw new EmptyListException();
        else return this.header.next.value;
    }

    public T last() throws EmptyListException {
        if (this.isEmpty()) throw new EmptyListException();
        else return this.trailer.prev.value;
    }

    @Override
    public String toString() {
        DListNode<T> currNode = header.next;
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        while(true) {
            sb.append(currNode.toString());
            currNode = currNode.next;
            if (currNode != trailer) {
                sb.append(',');
            }
            else break;
        }
        sb.append(']');
        return String.valueOf(sb);
    }

    public boolean isIncreasing(Comparator<T> comparator) {
        DListNode<T> node = header.next;
        if (this.size() == 0 || this.size() == 1) return true;    // vacuously true
        while(node.next!=trailer) {
            if (comparator.compare(node.value,node.next.value) > 0) return false;
            node = node.next;
        }
        return true;
    }

    public boolean isDecreasing(Comparator<T> comparator) {
        return this.isIncreasing(comparator.reversed());
    }

    @Override
    public ListIterator<T> iterator() {
        return new ListIterator<>(header, trailer);
    }

}

