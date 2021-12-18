// Class: Implementation of BST in A2
// Implement the following functions according to the specifications provided in Tree.java

public class BSTree extends Tree {

    private BSTree left, right;     // Children.
    private BSTree parent;          // Parent pointer.

    public BSTree(){
        super();
        // This acts as a sentinel root node
        // How to identify a sentinel node: A node with parent == null is SENTINEL NODE
        // The actual tree starts from one of the child of the sentinel node!.
        // CONVENTION: Assume right child of the sentinel node holds the actual root! and left child will always be null.
    }

    public BSTree(int address, int size, int key){
        super(address, size, key);
    }

    private boolean isSentinel() {
        if (parent == null) return true;
        return false;
    }

    private boolean isLeaf() {

        if (left == null && right == null) {
            return true;
        }
        return false;
    }

    private BSTree getRoot() {
        BSTree currNode = this;
        while (currNode.parent != null) {
            currNode = currNode.parent;
        }
        return currNode.right;
    }

    public BSTree Insert(int address, int size, int key)
    {
        BSTree newNode = new BSTree(address,size,key);
        if (this.parent == null && this.right == null) {
            this.right = newNode;
            newNode.parent = this;
            return newNode;
        }

        BSTree currNode = this.getRoot();
        while (currNode != null) {
            if (currNode.key < newNode.key) {
                if (currNode.right == null) {
                    currNode.right = newNode;
                    newNode.parent = currNode;
                    return newNode;
                }
                else currNode = currNode.right;
            }
            else {
                if (currNode.left == null) {
                    currNode.left = newNode;
                    newNode.parent = currNode;
                    return newNode;
                }
                else currNode = currNode.left;
            }
        }
        return null;
    }

    //Returns Min elem in the current subtree
    private BSTree getMin() {
        BSTree currNode = this;
        // currNode is sentinel
        if (currNode.parent == null) {
            currNode = currNode.right;
        }
        if (currNode == null) return null;
        while (currNode.left != null) {
            currNode = currNode.left;
        }
        return currNode;
    }

    // Leaf Node
    private void del_1Node() {
        if ((this.parent).left == this) this.parent.left = null;
        else this.parent.right = null;
        this.parent = null;
    }

    // Exactly 1 child
    private void del_2Node() {
        BSTree node;
        if (this.left == null) node = this.right;
        else node = this.left;
        if ((this.parent).left == this) this.parent.left = node;
        else this.parent.right = node;
        node.parent = this.parent;
    }
    // Exactly 2 children
    private void del_3Node() {
        BSTree sucNode;
        sucNode = this.getNext();
        // delNode here will either call del_1Node or del_2Node
        sucNode.delNode();
        this.key = sucNode.key;this.address = sucNode.address;this.size = sucNode.size;
    }

    // delNode deletes the current node from the tree
    private void delNode() {
        BSTree currNode = this;
        //leaf node
        if (this.left == null && this.right == null) {
            this.del_1Node();
        }
        //exactly 2 children
        else if (this.left != null && this.right != null) {
            this.del_3Node();
        }
        //exactly 1 child
        else this.del_2Node();
    }

    public boolean Delete(Dictionary e)
    {
        BSTree currNode = this.getRoot();
        while (currNode != null) {
            if (currNode.key < e.key) {
                currNode = currNode.right;
            }
            if (currNode == null) return false;
            if (currNode.key > e.key) {
                currNode = currNode.left;
            }
            if (currNode == null) return false;
            if (currNode.key == e.key) {
                if (currNode.address == e.address && currNode.size == e.size) {
                    currNode.delNode();
                    return true;
                }
                else currNode = currNode.left;         // because keys of equal size can only be on the left (from insertion def)
            }
        }
        return false;
    }

    public BSTree Find(int key, boolean exact)
    {
        BSTree currNode = this.getRoot();
        if (exact) {
            while (currNode != null) {
                if (currNode.key < key) {
                    currNode = currNode.right;
                }
                if (currNode == null) {
                    return null;
                }
                if (currNode.key > key) {
                    currNode = currNode.left;
                }
                if (currNode == null) {
                    return null;
                }
                if (currNode.key == key) return currNode;
            }
            return null;
        }
        else {
            // Node with the smallest key >= the given key
            BSTree minNode = currNode;
            if (currNode == null) {
                return null;
            }
            while (currNode != null) {
                if (currNode.key >= key) {
                    if (currNode.key <= minNode.key | minNode.key < key) {
                        minNode = currNode;
                    }
                    currNode = currNode.left;
                }
                else currNode = currNode.right;
            }
            if (key > minNode.key) return null;
            else return minNode;
        }
    }

    public BSTree getFirst()
    {
        BSTree currNode = this.getRoot();
        if (currNode == null) return null;
        return currNode.getMin();
    }


    // returns successor of the current node
    public BSTree getNext()
    {
        BSTree currNode = this;

        if (currNode.isSentinel()) return null;

        if (currNode.right != null) {
            currNode = currNode.right;
            return currNode.getMin();
        }
        else {
            BSTree parentNode = currNode.parent;
            while (!(parentNode.isSentinel()) && parentNode.right == currNode) {
                currNode = parentNode;
                parentNode = parentNode.parent;
            }
            // we were at the largest key
            if (parentNode.isSentinel()) return null;
            else return parentNode;
        }
    }

    // using hare and tortoise to detect a cycle in a linear chain of parent (which is like a singly linked list)
    private boolean isCycle() {

        BSTree tortoise = this;
        BSTree hare = this.parent;

        while (hare != null && hare.parent != null) {
            if (hare == tortoise) {
                return true;
            }
            tortoise = tortoise.parent;
            hare = hare.parent.parent;
        }
        return false;
    }
    // assuming the input is root use this method for invCheck for BinaryTrees
    private boolean rootInvCheck() {
        BSTree currNode = this;

        if (currNode.left != null) {
            if (currNode.left.parent != currNode) return false;
        }
        if (currNode.right != null) {
            if (currNode.right.parent != currNode) return false;
        }
        if (currNode.isLeaf()) return true;
        // exactly one of them is null or both are not null
        if (currNode.left == null | currNode.right ==  null) {
            if (currNode.left == null) return currNode.right.rootInvCheck();
            else return currNode.left.rootInvCheck();
        }
        // both are not null
        return ((currNode.left).rootInvCheck() && (currNode.right).rootInvCheck());
    }

    // keyInv check is called at the root

    private boolean keyInv() {
        BSTree currNode = this;

        if (currNode.left != null) {
            if (currNode.left.key > currNode.key) return false;
        }
        if (currNode.right != null) {
            if (currNode.right.key <= currNode.key) return false;
        }
        if (currNode.isLeaf()) return true;
        // exactly one of them is null or both are not null
        if (currNode.left == null | currNode.right ==  null) {
            if (currNode.left == null) return currNode.right.rootInvCheck();
            else return currNode.left.rootInvCheck();
        }
        // both are not null
        return currNode.left.keyInv() && currNode.right.keyInv();
    }

    private boolean invBSTree() {

        if (this.isCycle()) return false;

        BSTree currNode = this;
        BSTree parNode = this.parent;

        while (parNode != null) {
            if (parNode.left == parNode.right) return false;
            if (parNode.left == currNode | parNode.right == currNode) {
                currNode = parNode;
                parNode = parNode.parent;
            }
        }
        // currNode is sentinel node
        if (currNode.left != null) return false;
        // empty tree
        if (currNode.right == null) return true;
        if ((currNode.right).parent != currNode) return false;
        // we are at the root node now
        currNode = currNode.right;
        // write the code for checking inv from the root (recursive)
        return currNode.rootInvCheck() && currNode.keyInv();
    }

    public boolean sanity()
    {
        return this.invBSTree();
    }



}





