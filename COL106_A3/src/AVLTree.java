// Class: Height balanced AVL Tree
// Binary Search Tree

public class AVLTree extends BSTree {

    private AVLTree left, right;     // Children.
    private AVLTree parent;          // Parent pointer.
    private int height;  // The height of the subtree

    public AVLTree() {
        super();
        // This acts as a sentinel root node
        // How to identify a sentinel node: A node with parent == null is SENTINEL NODE
        // The actual tree starts from one of the child of the sentinel node !.
        // CONVENTION: Assume right child of the sentinel node holds the actual root! and left child will always be null.

    }

    public AVLTree(int address, int size, int key) {
        super(address, size, key);
        this.height = 0;
    }

    // Implement the following functions for AVL Trees.
    // You need not implement all the functions.
    // Some of the functions may be directly inherited from the BSTree class and nothing needs to be done for those.
    // Remove the functions, to not override the inherited functions.

    private static int max(int a , int b) {
        if (a <= b) return b;
        else return a;
    }

    private AVLTree getRoot() {
        AVLTree currNode = this;
        while (currNode.parent != null) {
            currNode = currNode.parent;
        }
        return currNode.right;
    }

    private boolean isSentinel() {
        if (parent == null) return true;
        return false;
    }

    //checks whether the node is height balanced;

    private boolean isLeaf() {

        if (left == null && right == null) {
            return true;
        }
        return false;
    }
    // htBalance check is assumed to be checked on a node of AVL Tree (NOT on sentinel)
    private boolean htBalCheck() {
        AVLTree currNode = this;
        if (currNode.isLeaf()) return true;
        if (currNode.left == null ^ currNode.right == null) {
            if (currNode.left == null) return currNode.right.isLeaf();
            if (currNode.right == null) return currNode.left.isLeaf();
        }
        int h1 = currNode.left.height;
        int h2 = currNode.right.height;
        if (h1-h2 == -1 | h1-h2 == 0 | h1-h2 == 1) return true;
        return false;
    }

    // u is parent of v
    private static void leftRot(AVLTree u,AVLTree v) {
        v.parent = u.parent;
        if (u.parent.left == u) u.parent.left = v;
        else u.parent.right = v;
        u.parent = v;
        u.right = v.left;
        if (v.left!=null) v.left.parent = u;
        v.left = u;
    }

    // v is parent of u
    private static void rightRot(AVLTree v,AVLTree u) {
        u.parent = v.parent;
        if (v.parent.left == v) v.parent.left = u;
        else v.parent.right = u;
        v.parent = u;
        v.left = u.right;
        if (u.right!=null) u.right.parent = v;
        u.right = v;
    }

    // assuming the correct input x.parent = y,y.parent = z
    private static boolean checkLinChain(AVLTree z,AVLTree y,AVLTree x) {
        if (z.left == y) {
            if (y.left == x) return true;
            return false;
        }
        if (z.right == y) {
            if (y.right == x) return true;
            return false;
        }
        return false;
    }

    // updates the height of the current node
    private void htSet() {
        int h1,h2;
        if (this.left == null) {
            h1 = 0;
        }
        else {
            h1 = this.left.height;
        }
        if (this.right == null) {
            h2 = 0;
        }
        else {
            h2 = this.right.height;
        }
        this.height = max(h1 , h2) + 1;
    }

    public AVLTree Insert(int address, int size, int key)
    {
        AVLTree x = new AVLTree(address,size,key);
        AVLTree newNode = x;
        if (this.parent == null && this.right == null) {
            this.right = x;
            x.parent = this;
            x.height++;
            return x;
        }

        AVLTree y = this.getRoot();
        while (y != null) {
            if (y.key < x.key) {
                if (y.right == null) {
                    y.right = x;
                    x.parent = y;
                    x.height++;
                    break;
                }
                else {
                    y = y.right;
                }
            }
            if (y.key > x.key) {
                if (y.left == null) {
                    y.left = x;
                    x.parent = y;
                    x.height++;
                    break;
                }
                else {
                    y = y.left;
                }
            }
            else {
                if (y.address < x.address) {
                    if (y.right == null) {
                        y.right = x;
                        x.parent = y;
                        x.height++;
                        break;
                    }
                    else {
                        y = y.right;
                    }
                }
                else {
                    if (y.left == null) {
                        y.left = x;
                        x.parent = y;
                        x.height++;
                        break;
                    }
                    else {
                        y = y.left;
                    }
                }
            }
        }

        y.htSet();
        AVLTree z = y.parent;

        while (!z.isSentinel() && z.htBalCheck()) {

            z.htSet();
            x = y;
            y = z;
            z = z.parent;
        }
        if (z.isSentinel()) return newNode;
        else {
            if (checkLinChain(z,y,x)) {
                if (z.left == y) {
                    rightRot(z,y);
                    x.htSet();
                    z.htSet();
                    y.htSet();
                }
                else {
                    leftRot(z,y);
                    x.htSet();
                    z.htSet();
                    y.htSet();
                }
            }
            else {
                if (z.left == y) {
                    leftRot(y,x);
                    rightRot(z,x);
                    y.htSet();
                    z.htSet();
                    x.htSet();
                }
                else {
                    rightRot(y,x);
                    leftRot(z,x);
                    y.htSet();
                    z.htSet();
                    x.htSet();
                }
            }
            return newNode;
        }
    }

    // it will act on an internal node
    private AVLTree childLargeht() {
        if (this.isSentinel()) return this.right;
        if (this.isLeaf()) return null;
        if (this.left == null) return this.right;
        if (this.right == null) return this.left;
        if (this.left.height < this.right.height) return this.right;
        if (this.left.height == this.right.height) {
            // note that this.parent cannot be null
            if (this.parent.left == this) return this.left;
            else return this.right;
        }
        else return this.left;
    }

    // called on parent of deleted node subtree
    private void deleteHelp() {

        if (this.isSentinel()) {
            return;
        }
        AVLTree z = this;

        while (!z.isSentinel() && z.htBalCheck()) {
            z.htSet();
            z = z.parent;
        }
        if (z.isSentinel()) {
            return;
        }
        // z is the first node where height imbalance occurs
        AVLTree y = z.childLargeht();
        AVLTree x = y.childLargeht();
        if (checkLinChain(z,y,x)) {
            if (z.left == y) {
                rightRot(z,y);
                x.htSet();
                z.htSet();
                y.htSet();
                y.parent.deleteHelp();
            }
            else {
                leftRot(z,y);
                x.htSet();
                z.htSet();
                y.htSet();
                y.parent.deleteHelp();
            }
        }
        else {
            if (z.left == y) {
                leftRot(y,x);
                rightRot(z,x);
                y.htSet();
                z.htSet();
                x.htSet();
                x.parent.deleteHelp();
            }
            else {
                rightRot(y,x);
                leftRot(z,x);
                y.htSet();
                z.htSet();
                x.htSet();
                x.parent.deleteHelp();
            }
        }
        return;
    }

    // All deletions in an AVL tree can be effectively assumed to be at the leaf
    // deletes the current Node which is leaf (i/p is leaf by assumption)
    private void del_Leaf() {
        AVLTree z = this;
        if (z.isSentinel()) {
            return;
        }
        if (z.parent.isSentinel()) {
            z.parent.right = null;
            return;
        }
        z = z.parent;
        if (z.left == this) z.left = null;
        else z.right = null;
        z.deleteHelp();
    }

    // deletes the current node
    private void delNode() {
        AVLTree currNode = this;

        if (currNode.isLeaf()) {
            currNode.del_Leaf();
            return;
        }
        if (currNode.left == null ^ currNode.right == null) {
            if (currNode.left == null) {
                currNode.right.parent = currNode.parent;
                if (currNode.parent.left == currNode) currNode.parent.left=currNode.right;
                else currNode.parent.right = currNode.right;
                currNode = currNode.right;
                currNode.deleteHelp();
            }
            else {
                currNode.left.parent = currNode.parent;
                if (currNode.parent.left == currNode) currNode.parent.left=currNode.left;
                else currNode.parent.right = currNode.left;
                currNode = currNode.left;
                currNode.deleteHelp();
            }
            return;
        }
        else {
            AVLTree sucNode;
            sucNode = this.getNext();
            sucNode.delNode();
            AVLTree newNode = new AVLTree(sucNode.address, sucNode.size, sucNode.key);
            newNode.parent = this.parent;
            if (this.parent.left == this) this.parent.left = newNode;
            else this.parent.right = newNode;
            newNode.left = this.left;
            if (this.left!=null) this.left.parent = newNode;
            newNode.right = this.right;
            if (this.right!=null) this.right.parent = newNode;
            newNode.height = this.height;
            return;
        }
    }

    public boolean Delete(Dictionary e)
    {
        AVLTree currNode = this.getRoot();
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
                else {
                    if (currNode.address < e.address) currNode = currNode.right;
                    else currNode = currNode.left;
                }
            }
        }
        return false;
    }

    public AVLTree Find(int key, boolean exact)
    {
        AVLTree currNode = this.getRoot();
        if (currNode == null) {
            return null;
        }
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
            AVLTree minNode = currNode;
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

    private AVLTree getMin() {
        AVLTree currNode = this;
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

    public AVLTree getFirst()
    {
        AVLTree currNode = this.getRoot();
        if (currNode == null) return null;
        return currNode.getMin();
    }

    // returns successor of the current node
    public AVLTree getNext()
    {
        AVLTree currNode = this;

        if (currNode.isSentinel()) return null;

        if (currNode.right != null) {
            currNode = currNode.right;
            return currNode.getMin();
        }
        else {
            AVLTree parentNode = currNode.parent;
            while (!(parentNode.isSentinel()) && parentNode.right == currNode) {
                currNode = parentNode;
                parentNode = parentNode.parent;
            }
            // we were at the largest key
            if (parentNode.isSentinel()) return null;
            else return parentNode;
        }
    }

    private boolean isCycle() {

        AVLTree tortoise = this;
        AVLTree hare = this.parent;

        while (hare != null && hare.parent != null) {
            if (hare == tortoise) {
                return true;
            }
            tortoise = tortoise.parent;
            hare = hare.parent.parent;
        }
        return false;
    }

    private boolean rootInvCheck() {
        AVLTree currNode = this;

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

    private boolean keyInv() {
        AVLTree currNode = this;

        if (currNode.left != null) {
            if (currNode.left.key > currNode.key) return false;
            if (currNode.left.key == currNode.key) {
                if (currNode.left.address > currNode.address) return false;
            }
        }
        if (currNode.right != null) {
            if (currNode.right.key < currNode.key) return false;
            if (currNode.right.key == currNode.key) {
                if (currNode.right.address < currNode.address) return false;
            }
        }
        if (currNode.isLeaf()) return true;
        // exactly one of them is null or both are not null
        if (currNode.left == null ^ currNode.right ==  null) {
            if (currNode.left == null) return currNode.right.keyInv();
            else return currNode.left.keyInv();
        }
        // both are not null
        return currNode.left.keyInv() && currNode.right.keyInv();
    }

    // assuming we're at the root node
    // Via recursion
    private boolean heightInv() {

        AVLTree currNode = this;
        if (!currNode.htBalCheck()) return false;
        if (currNode.isLeaf()) return true;
        if (currNode.left == null) return currNode.right.isLeaf();
        if (currNode.right == null) return currNode.left.isLeaf();
        else {
            return (currNode.left.heightInv() && currNode.right.heightInv());
        }
    }

    private boolean invAVLTree() {

        if (this.isCycle()) return false;

        AVLTree currNode = this;
        AVLTree parNode = this.parent;

        while (!parNode.isSentinel()) {
            // parNode is an internal node and NOT a leaf
            if (parNode.left == currNode ^ parNode.right == currNode) {
                currNode = parNode;
                parNode = parNode.parent;
            }
        }
        // currNode is Root Node
        if (parNode.left != null) return false;
        // empty tree
        // write the code for checking inv from the root (recursive)
        return currNode.rootInvCheck() && currNode.keyInv() && currNode.heightInv();
    }

    public boolean sanity()
    {
        return this.invAVLTree();
    }

}


