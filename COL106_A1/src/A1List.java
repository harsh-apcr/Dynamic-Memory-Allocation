// Implements Dictionary using Doubly Linked List (DLL)
// Implement the following functions using the specifications provided in the class List

public class A1List extends List {

    private A1List next; // Next Node
    private A1List prev;  // Previous Node

    public A1List(int address, int size, int key) {
        super(address, size, key);
    }

    public A1List(){
        super(-1,-1,-1);
        // This acts as a head Sentinel

        A1List tailSentinel = new A1List(-1,-1,-1); // Initiate the tail sentinel

        this.next = tailSentinel;
        tailSentinel.prev = this;
    }

    public A1List Insert(int address, int size, int key)
    {
        A1List newNode = new A1List(address,size,key);
        newNode.prev = this;
        newNode.next = this.next;
        (this.next).prev = newNode;
        this.next = newNode;
        return newNode;
    }

    private boolean isSentinel() {
        if (this.next == null | this.prev == null) return true;
        return false;
    }

    public boolean Delete(Dictionary d) {
        int key = d.key;
        A1List node1 = this;
        A1List node2 = this;
        if (node2.prev == null) {
            node1 = node1.next;
        }
        if (node1.next == null) {
            node2 = node2.prev;
        }
        if (node1 == null | node2 ==null ) return false;
        while (!node1.isSentinel() | !node2.isSentinel()) {

            if (node1.key == key | node2.key == key) {
                A1List node;
                if (node1.key == key) node = node1;
                else node = node2;
                if (node.address == d.address && node.size == d.size) {
                    if (node.isSentinel()) return false;
                    else {
                        (node.next).prev = node.prev;
                        (node.prev).next = node.next;
                        return true;
                    }
                }
            }
            if (!node1.isSentinel()) node1 = node1.next;
            if (!node2.isSentinel()) node2 = node2.prev;
        }
        return false;
    }

    public A1List Find(int k, boolean exact)        // for allocation - key is address for free - key is size
    {
        A1List node1 = this;
        A1List node2 = this;
        if (node2.prev == null) {
            node1 = node1.next;
        }
        if (node1.next == null) {
            node2 = node2.prev;
        }
        if (node1 == null | node2 ==null ) return null;
        if (exact) {
            while (!node1.isSentinel() | !node2.isSentinel()) {
                if (node1.key == k | node2.key == k) {
                    A1List node;
                    if (node1.key == k) node = node1;
                    else node = node2;
                    return node;
                }
                if (!node1.isSentinel()) node1 = node1.next;
                if (!node2.isSentinel()) node2 = node2.prev;
            }
            return null;
        }
        else {
            while (!node1.isSentinel() | !node2.isSentinel()) {
                if (node1.key >= k | node2.key >= k) {
                    A1List node;
                    if (node1.key >= k) node = node1;
                    else node = node2;
                    return node;
                }
                if (!node1.isSentinel()) node1 = node1.next;
                if (!node2.isSentinel()) node2 = node2.prev;
            }
        }
        return null;
    }

    public A1List getFirst()
    {
        if (this == null) {
            return null;
        }
        A1List node = this;
        while (node.prev != null) {
            node = node.prev;
        }
        return node.next;
    }

    public A1List getNext()
    {
        if (this.next.next == null) {
            return null;
        }
        else {
            return this.next;
        }
    }
    
    private boolean invCheck() {
        A1List node1 = this;
        A1List node2 = this;
        if (node2.prev == null) {
            node1 = node1.next;
        }
        if (node1.next == null) {
            node2 = node2.prev;
        }
        if (node1 == null | node2 ==null ) return false;
        while (!node1.isSentinel() | !node2.isSentinel()) {

            if (!node1.isSentinel() && node1.next == null) return false;
            if (!node1.isSentinel() && (node1.next).prev != node1) return false;
            if (!node2.isSentinel() && node2.prev == null) return false;
            if (!node2.isSentinel() && (node2.prev).next != node2) return false;
            if (!node1.isSentinel()) node1 = node1.next;
            if (!node2.isSentinel()) node2 = node2.prev;
        }
        if (node1.next != null | node2.prev != null) return false;
        return true;
    }

    private boolean isCircular() {
        A1List node1 = this;
        A1List node2 = this;
        if (node1.next == null | node2.prev == null) {
            return false;
        }
        else {
            while (node1 != null && node2 != null) {
                node1 = node1.next;
                if (node1 == node2) return true;
                node2 = node2.prev;
            }
            return false;
        }
    }

    public boolean sanity()
    {
        return (!this.isCircular() && this.invCheck());
    }

}


