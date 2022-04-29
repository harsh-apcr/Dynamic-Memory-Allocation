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
        // `this` acts as a head Sentinel
        A1List tailSentinel = new A1List(-1,-1,-1); // Initiate the tail sentinel

        this.next = tailSentinel;
        tailSentinel.prev = this;
    }

    /**
     * Inserts the element in the list after the current node
     * @param address int (starting address of the block)
     * @param size int (size of the block)
     * @param key int (key on which this block is indexed)
     */
    @Override
    public List insert(int address, int size, int key) {
        A1List newNode = new A1List(address, size, key);
        newNode.prev = this;
        newNode.next = this.next;
        this.next.prev = newNode;
        this.next = newNode;
        return newNode;
    }

    /**
     * Deletes the entry corresponding to d from the DLL.
     * @param d dictionary to be deleted
     * @return true if d is found , false otherwise
     */
    @Override
    public boolean delete(Dictionary d) {
        A1List backNode = this.key != -1 ? this :
                (this.prev == null) ? this.next : // this.key == -1 and this.prev == null <=> this is head sentinel
                this.prev;  // this.key == -1 and this.prev != null <=> this is tail sentinel
        A1List fwdNode = backNode;

        if (backNode.key == -1) {
            // backNode.key == -1 means that the list is empty
            return false;
        }
        // backNode.key != -1 and fwdNode.key != -1

        while (backNode.key != -1 || fwdNode.key != -1) {
            if (backNode.key == d.key && backNode.address == d.address && backNode.size == d.size) {
                // found node to be deleted
                if (backNode == this) {
                    backNode = backNode.next;   // this is not head or tail so blackNode != null
                    // now delete back-node
                    backNode.prev.next = backNode.next; // blackNode.prev == this != null
                    // backNode.next could be null
                    if (backNode.next == null) {
                        this.copyNodes(backNode);
                        backNode.prev = null;
                        this.next = null;
                    }
                    else {
                        backNode.next.prev = backNode.prev;
                        backNode.prev.copyNodes(backNode);
                    }
                }
                else {
                    // backNode is deleted from the list
                    backNode.prev.next = backNode.next;
                    backNode.next.prev = backNode.prev;
                }
                return true;
            }
            if (fwdNode.key == d.key && fwdNode.address == d.address && fwdNode.size == d.size) {
                // found node to be deleted
                // note fwdNode cannot be this , as backNode == this is already handled
                // fwdNode is deleted from the list
                fwdNode.prev.next = fwdNode.next;
                fwdNode.next.prev = fwdNode.prev;
                return true;
            }
            if (backNode.key != -1) {backNode = backNode.prev;}
            if (fwdNode.key != -1) {fwdNode = fwdNode.next;}
        }
        return false;
    }

    /**
     * Finds the block with key == k ,if exact is true.
     * else finds the block with key >= k (approximate find)
     * @param k int (key of the block to find)
     * @param exact boolean (if true then exact search takes place , else approximate search)
     * @return List block, if found else null.
     */
    @Override
    public List find(int k, boolean exact) {
        A1List backNode = this.key != -1 ? this :
                (this.prev == null) ? this.next : // this.key == -1 and this.prev == null <=> this is head sentinel
                        this.prev;  // this.key == -1 and this.prev != null <=> this is tail sentinel
        A1List fwdNode = backNode;

        if (backNode.key == -1) {
            // backNode.key == -1 means that the list is empty
            return null;
        }

        // backNode.key != -1 and fwdNode.key != -1
        if (exact) {
            // return a block with key == k
            while (backNode.key != -1 || fwdNode.key != -1) {
                if (backNode.key == k) {
                    return backNode;
                }
                if (fwdNode.key == k) {
                    return fwdNode;
                }
                if (backNode.key != -1) {backNode = backNode.prev;}
                if (fwdNode.key != -1) { fwdNode = fwdNode.next; }
            }
        }
        else {
            // return a block with key >= k
            while (backNode.key != -1 || fwdNode.key != -1) {
                if (backNode.key >= k) {
                    return backNode;
                }
                if (fwdNode.key >= k) {
                    return fwdNode;
                }
                if (backNode.key != -1) {backNode = backNode.prev;}
                if (fwdNode.key != -1) { fwdNode = fwdNode.next; }
            }
        }
        return null;
    }

    @Override
    public List getFirst() {
        A1List currNode = this;
        while (currNode.key != -1) {
            currNode = currNode.prev;
        }
        if (currNode.next.key == -1) {
            // currNode.key == -1 and currNode.next.key == -1
            // List is empty
            return null;
        }
        return currNode.next;
    }

    @Override
    public List getNext() {
        return this.next;
    }

    @Override
    public boolean sanity() {
        // goto head node (if sane head-node is present)
        A1List currNode = this;
        if (currNode.key == -1 && currNode.prev == null) {
            // currNode is a head-node
            // currNode.prev == null
            if (currNode.next == null) {return false;}      // no tail sentinel
            if (currNode.next.prev != currNode) {return false;}     // no proper link b/w `head = node`
            currNode = currNode.next;
            try {
                // this loop will terminate because there can be no cycle in DLL that do not involve at least one sentinel node
                while (currNode.key != -1) {
                    if (currNode.next.prev != currNode) return false;
                    currNode = currNode.next;
                }
            } catch(NullPointerException exc) {
                // currNode.next == null and currNode.key != -1
                // currNode in not a sentinel node but is at tail
                return false;
            }
            // (currNode.key == -1)
            if (currNode.next != null) return false;
        }
        else {
            // currNode is not a head node

            // either prev == null and key != -1 or key == -1 but prev != null
            if (currNode.prev == null ^ currNode.key == -1) return false;
            else {
                // (AB)'(AB' + BA')' = (A'+B')(A'+B)(A+B') = (A'+B'B)(A+B') = A'(A+B') = A'B'
                // currNode.prev != null and currNode.key != -1

                // this while loop will terminate as a cycle in DLL must contain a sentinel node
                try {
                    while (currNode.key != -1) {
                        if (currNode.prev.next != currNode) return false;
                        currNode = currNode.prev;
                    }
                } catch (NullPointerException exc) {
                    // currNode.prev == null
                    // currNode.key != -1
                    // currNode is not a sentinel node
                    return false;
                }
                // if currNode is a tail-node or a head-node with non-null prev then return false
                // currNode.key == -1
                if (currNode.prev != null) return false;
                else {
                    // currNode.key == -1 && currNode.prev == null
                    // currNode acts like a sane head-node
                    return currNode.sanity();
                }
            }
        }
        return true;
    }

    // Copies the content of `node` into this
    private void copyNodes(A1List node) {
        this.key = node.key;
        this.address = node.address;
        this.size = node.size;
    }

    public String toString() {
        return "{Memory Block : [" + this.address + "-->" + (this.address+this.size) + "]"
                +  ", key = " + this.key + "}";
    }

//     public static void main(String[] args) {
//         // testing
//         List myList = new A1List();
//         myList.insert(0,5,5);
//         myList.insert(5,10,10);
//         myList.insert(152, 15, 15);
//         myList.insert(213,12,12);
// //        for(List itr = myList.getFirst(); itr.getNext() != null; itr = itr.getNext()) {
// //            System.out.println(itr);
// //        }

//         // testing out sanity function
//         A1List myA1List = (A1List) myList;
//         myA1List = myA1List.next.next.next.next;
//         myA1List.next = myA1List.prev.prev.prev;
//         System.out.println(myA1List.sanity());


//     }

}


