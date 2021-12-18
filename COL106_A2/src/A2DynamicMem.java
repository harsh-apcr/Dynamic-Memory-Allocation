// Class: A2DynamicMem
// Implements Degragment in A2. No other changes should be needed for other functions.

public class A2DynamicMem extends A1DynamicMem {

    public A2DynamicMem() {  super(); }

    public A2DynamicMem(int size) { super(size); }

    public A2DynamicMem(int size, int dict_type) { super(size, dict_type); }

    // In A2, you need to test your implementation using BSTrees and AVLTrees. 
    // No changes should be required in the A1DynamicMem functions. 
    // They should work seamlessly with the newly supplied implementation of BSTrees and AVLTrees
    // For A2, implement the Defragment function for the class A2DynamicMem and test using BSTrees and AVLTrees. 
    //Your BST (and AVL tree) implementations should obey the property that keys in the left subtree <= root.key < keys in the right subtree. How is this total order between blocks defined? It shouldn't be a problem when using key=address since those are unique (this is an important invariant for the entire assignment123 module). When using key=size, use address to break ties i.e. if there are multiple blocks of the same size, order them by address. Now think outside the scope of the allocation problem and think of handling tiebreaking in blocks, in case key is neither of the two. 
    public void Defragment() {
        if (this.freeBlk == null) {
            return;
        }
        Dictionary FMB = this.freeBlk.getFirst();
        if (FMB == null) {
            return;
        }
        Dictionary freeBST = new BSTree();
        while (FMB != null) {
            freeBST.Insert(FMB.address, FMB.size, FMB.address);
            FMB = FMB.getNext();
        }
        // you got freeBST indexed by address
        freeBST = freeBST.getFirst();
        Dictionary nextInFree = freeBST.getNext();
        while (nextInFree != null) {
            if (freeBST.address + freeBST.size == nextInFree.address) {
                freeBST.size = nextInFree.size+freeBST.size;
                freeBST.Delete(nextInFree);
            }
            else {
                freeBST = nextInFree;
            }
            nextInFree = freeBST.getNext();
        }
        FMB = freeBST.getFirst();
        Dictionary newFreeBlk = new BSTree();

        while (FMB != null) {
            newFreeBlk.Insert(FMB.address,FMB.size,FMB.size);
            FMB = FMB.getNext();
        }
        freeBlk = newFreeBlk;
        return ;
    }

}